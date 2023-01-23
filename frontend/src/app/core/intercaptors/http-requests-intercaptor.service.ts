import {
  HttpEvent,
  HttpEventType,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse,
  HttpStatusCode,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { AuthService } from '../global-services/auth.service';
@Injectable()
export class HttpRequestsIntercaptorService implements HttpInterceptor {
  public AUTHORIZATION = 'authorization';
  public TOKEN_PREFIX = 'Bearer ';
  public ACCESS_TOKEN_HEADER = 'access_token';
  public REFRESH_TOKEN_COOKIE = 'jit';
  public ENDPOINT_WICH_RETURNS_TOKEN: string[] = [];

  constructor(private authService: AuthService) {
    this.ENDPOINT_WICH_RETURNS_TOKEN = [
      authService.loginEndpoint,
      authService.refreshTokenEndpoint,
    ];
  }
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const accessToken = this.authService.accessToken;
    if (accessToken !== '') {
      req.headers.append('authorization', accessToken);
    }
    console.log('login');

    req = req.clone({
      withCredentials: true,
    });

    return next.handle(req).pipe(
      tap((response) => {
        if (
          response.type === HttpEventType.Response &&
          this.ENDPOINT_WICH_RETURNS_TOKEN.includes(response.url ?? '') &&
          response.status === HttpStatusCode.Ok
        ) {
          this.onLoginSuccess(response);
        }
        console.log(response);
      })
    );
  }
  onLoginSuccess(response: HttpResponse<any>) {
    const accessToken = response.headers.get(this.AUTHORIZATION);
    if (accessToken) this.authService.accessToken = accessToken;
    this.authService.alterAuthState(true);
  }
}
