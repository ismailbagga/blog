import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Tag, TagWithCount } from './http-article.service';
import { catchError } from 'rxjs';
import { tagCreateErrorMapper } from '../utils/ExceptionsMappers';

@Injectable({
  providedIn: 'root',
})
export class TagHttpService {
  tagsEndpoint = `${environment.backendUrl}/api/v1/tags`;

  constructor(private http: HttpClient) {}
  saveTag(payload: { title: string; slug: string }) {
    return this.http.post<Tag>(this.tagsEndpoint, payload).pipe(
      catchError((err) => {
        throw tagCreateErrorMapper(err);
      })
    );
  }
  editTag(payload: Tag) {
    return this.http.put<Tag>(this.tagsEndpoint, payload).pipe(
      catchError((err) => {
        throw tagCreateErrorMapper(err);
      })
    );
  }
  fetchAllTagWithCount() {
    return this.http.get<TagWithCount[]>(this.tagsEndpoint);
  }
  findAllTags() {
    return this.http.get<Tag[]>(`${this.tagsEndpoint}/basic`);
  }
  deleteTag(tagId: number) {
    return this.http.delete(`${this.tagsEndpoint}/id/${tagId}`);
  }
  searchforTagsByTitle(term: string) {
    const url = `${this.tagsEndpoint}/search/title/${term}`;
    return this.http.get<Tag[]>(url);
  }
  findTagBySlug(slug: string) {
    return this.http.get<Tag>(`${this.tagsEndpoint}/details/slug/${slug}`);
  }
}
