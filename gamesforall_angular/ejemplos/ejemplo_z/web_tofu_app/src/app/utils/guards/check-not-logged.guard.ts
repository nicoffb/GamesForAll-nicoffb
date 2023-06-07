import { Injectable } from '@angular/core';

import { Observable, map, take } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class CheckNotLoggedGuard {
  constructor(private authService: AuthService, private router: Router) {}
  canActivate(): Observable<boolean> {
    return this.authService.isLogged.pipe(
      take(1),
      map((isLogged: boolean) => {
        if(isLogged){
            this.router.navigate(['home']);
          }
        return !isLogged})
    );
  }
}
