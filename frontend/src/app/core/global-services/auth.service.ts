import { error } from 'console';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _access_token: string = '';
  private authState$ = new BehaviorSubject<boolean | null>(null);
  get accessToken() {
    return this._access_token;
  }
  set accessToken(token: string) {
    this._access_token = token;
  }
  authEdpoint = `${environment.backendUrl}/api/v1/auth`;
  loginEndpoint = `${this.authEdpoint}/login`;
  refreshTokenEndpoint = `${this.authEdpoint}/refresh`;

  constructor(private http: HttpClient) {
    const self = this;
  }

  alterAuthState(state: boolean) {
    return this.authState$.next(state);
  }
  stateAsObserbavle() {
    return this.authState$.asObservable();
  }

  login(username: string, password: string) {
    return this.http.post(this.loginEndpoint, { username, password }).pipe(
      catchError((err) => {
        this.alterAuthState(false);
        throw err;
      })
    );
  }
  refreshingToken() {
    if (document.cookie.indexOf('SECURITY') === -1) {
      this.alterAuthState(false);
      return of([]);
    }

    return this.http.post(this.refreshTokenEndpoint, {}).pipe(
      catchError((err) => {
        this.alterAuthState(false);
        throw err;
      })
    );
  }
}
