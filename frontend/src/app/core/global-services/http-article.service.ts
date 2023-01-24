import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { CloudinaryResponse, ImageService } from './image.service';
import { Observable, catchError, mergeMap, switchMap } from 'rxjs';
import { handlingArticleUploadExceptions } from '../utils/ExceptionsMappers';
// ---------------- Types -------------------
export type ArticlePreviwWithResultCount = {
  articlePreviews: ArticlePreview[];
  count: number;
};
export type ArticlePreview = {
  id: number;
  title: string;
  slug: number;
  description: number;
  url: string;
  createdAt: Date;
  updatedAt: Date;
  relatedTags: Set<Tag>;
};
export type Tag = {
  id: number;
  title: string;
  slug: string;
};
export type TagWithArticles = {
  id: number;
  title: string;
  slug: string;
  relatedArticles: ArticlePreview[];
};
export type TagWithCount = {
  id: number;
  title: string;
  slug: string;
  count: number;
};

export type ArticleFormValues = {
  image: File;
  tagIds: number[];
  readingTime: number;
  title: string;
  slug: string;
  description: string;
  content: string;
};

export type ArticleUploadDto = {
  title: string;
  slug: string;
  tagIds: number[];
  readingTime: number;
  description: string;
  content: string;
  imagePayload: { version: string; signature: string; url: string };
};
export type ArticleSlug = { articleSlug: string };
export type Pipelevel = 0 | 1 | 2;
@Injectable({
  providedIn: 'root',
})
export class ArticleHttpService {
  articlesEndpoint = `${environment.backendUrl}/api/v1/articles`;
  tagsEndpoint = `${environment.backendUrl}/api/v1/tags`;

  constructor(private http: HttpClient, private imageService: ImageService) {}
  uploadArticle(
    form: ArticleFormValues,
    forceSignatureFetch = false,
    recursiveCallCount = 0
  ) {
    let pipeLevel: Pipelevel = 0;
    if (recursiveCallCount >= 2)
      throw new Error('trying many time to retrieve signature');
    const result: Observable<ArticleSlug> = this.imageService
      .getSignature(forceSignatureFetch)
      .pipe(
        mergeMap((signature) => {
          pipeLevel = 1;
          return this.imageService.saveImage(form.image, signature);
        }),
        mergeMap((cloudinaryResponse: CloudinaryResponse) => {
          pipeLevel = 2;
          return this.http.post<ArticleSlug>(
            `${this.articlesEndpoint}`,
            this.articleUploadRequestBody(form, cloudinaryResponse)
          );
        }),
        catchError((err) => {
          if (this.imageService.shouldIRefreshSignature(err))
            return this.uploadArticle(form, true, recursiveCallCount + 1);
          throw handlingArticleUploadExceptions(err, pipeLevel);
        })
      );
    return result;
  }
  private articleUploadRequestBody(
    form: ArticleFormValues,
    cloudinaryResponse: CloudinaryResponse
  ): ArticleUploadDto {
    return {
      // from form submision
      title: form.title,
      slug: form.slug,
      description: form.description,
      content: form.content,
      tagIds: form.tagIds,
      readingTime: form.readingTime,
      // from cloudinary response
      imagePayload: {
        signature: cloudinaryResponse.signature,
        version: cloudinaryResponse.version,
        url: cloudinaryResponse.public_id,
      },
    };
  }
  fetchAllTagWithCount() {
    return this.http.get<TagWithCount[]>(this.tagsEndpoint);
  }
  fetchLatestArticles() {
    return this.http.get<ArticlePreview[]>(this.articlesEndpoint);
  }
  searchByTitle(title: string, page: number = 1) {
    const searchEndpoint = `${this.articlesEndpoint}/search/title/${page - 1}`;
    const params = new HttpParams().append('term', title);
    return this.http.get<ArticlePreview[]>(searchEndpoint, {
      params,
    });
  }
  searchByTitleWithResultCount(title: string, page: number = 1) {
    const searchEndpoint = `${this.articlesEndpoint}/search/title/total/${
      page - 1
    }`;
    const params = new HttpParams().append('term', title);
    return this.http.get<ArticlePreviwWithResultCount>(searchEndpoint, {
      params,
    });
  }
  fetchTagWithRelatedArticleBySlug(slug: string) {
    const url = `${this.tagsEndpoint}/related/${slug}/articles`;
    return this.http.get<TagWithArticles>(url);
  }
  deleteArticle(articleId: number) {
    return this.http.delete(`${this.articlesEndpoint}/${articleId}`);
  }
  searchforTagsByTitle(term: string) {
    const url = `${this.tagsEndpoint}/search/title/${term}`;
    return this.http.get<Tag[]>(url);
  }
}
