import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
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
@Injectable({
  providedIn: 'root',
})
export class ArticleHttpService {
  articlesEndpoint = `${environment.backendUrl}/api/v1/articles`;
  tagsEndpoint = `${environment.backendUrl}/api/v1/tags`;

  constructor(private http: HttpClient) {}
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
