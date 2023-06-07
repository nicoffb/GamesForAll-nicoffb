import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable, map, take } from 'rxjs';
import { AuthService } from '../../services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class CheckLoginGuard {
  constructor(private authService: AuthService, private router: Router) {}
  canActivate(): Observable<boolean> {
    return this.authService.isLogged.pipe(
      take(1),
      map((isLogged: boolean) => {
        if(!isLogged){
          this.router.navigate(['login']);
        }
        return isLogged
      })
    );
  }
}
