import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanLoad,
  Route,
  RouterStateSnapshot,
  UrlSegment,
  UrlTree,
} from '@angular/router';
import { Observable, map, skipWhile, take } from 'rxjs';
import { AuthService } from '../global-services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class LoginStateGuard implements CanActivate, CanLoad {
  constructor(private authService: AuthService) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    return true;
  }
  canLoad(
    route: Route,
    segments: UrlSegment[]
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    return this.authService.stateAsObserbavle().pipe(
      skipWhile((state) => state === null),
      map((value) => {
        console.log(value);
        if (value) return true;
        return false;
      }),
      take(1)
    );
  }
}
