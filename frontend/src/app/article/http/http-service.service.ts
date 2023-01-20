import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
// ---------------- Types -------------------

export type ArticlePreview = {
  id: number;
  title: number;
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
export type TagWithCount = {
  id: number;
  title: string;
  slug: string;
  count: number;
};
@Injectable({
  providedIn: 'root',
})
export class HttpServices {
  articlesEndpoint = `${environment.backendUrl}/api/v1/articles`;
  tagsEndpoint = `${environment.backendUrl}/api/v1/tags`;

  constructor(private http: HttpClient) {}
  fetchAllTagWithCount() {
    return this.http.get<TagWithCount[]>(this.tagsEndpoint);
  }
  fetchLatestArticles() {
    return this.http.get<ArticlePreview[]>(this.articlesEndpoint);
  }
}
