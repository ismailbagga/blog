import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
// ---------------- Types -------------------
export type TagWithCount = {
  id: number;
  title: string;
  slug?: string;
  count?: number;
};
@Injectable({
  providedIn: 'root',
})
export class HttpServices {
  articlesEndpoints = `${environment.backendUrl}/api/v1/articles`;
  tagsEndpoints = `${environment.backendUrl}/api/v1/tags`;

  constructor(private http: HttpClient) {}
  fetchAllTagWithCount() {
    return this.http.get<TagWithCount[]>(this.tagsEndpoints);
  }
}
