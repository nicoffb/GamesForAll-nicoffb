import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import {
  Observable,
  catchError,
  throwError,
} from 'rxjs';

import { ServerError } from 'src/app/models/error.interface';
import { NavigationExtras, Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    // const req = request.clone({
    //   withCredentials: true,
    // });

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
          switch (error.status) {
            case 401:
              
              if(error.error.message == "Bad credentials"){
                alert("Usuario o contraseña incorrectos")
              }
              break;
            case 404:
              this.showAlertError(error)
              break;
            case 403:
              this.authService.logout()
              break;
            default:
              this.showAlertError(error)
              break;
          }            
        return throwError(() => error);
      })
    );
  }

  private showAlertError(error: HttpErrorResponse) {
    if ('subErrors' in error.error) {
      const err: ServerError = error.error;
      alert(err.subErrors[0].message);
    } else {
      alert(error.error.message);
    }
  }
}
