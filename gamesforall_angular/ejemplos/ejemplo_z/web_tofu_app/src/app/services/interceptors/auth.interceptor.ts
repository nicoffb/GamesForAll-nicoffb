import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { catchError, finalize, Observable, switchMap, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService : AuthService){}
  private isRefreshing = false;

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    
    const token = localStorage.getItem('token');
    if (token !== null) {
      request = this.addTokenToRequest(request, token);
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401 && !request.url.includes('/auth/login')) {
          return this.handleAuthError(request, next);
        }
        return throwError(() => error);
      })
    );
  }

  private addTokenToRequest(
    request: HttpRequest<unknown>,
    token: string
  ): HttpRequest<unknown> {
    return request.clone({
      setHeaders: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
    });
  }

  private handleAuthError(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    if (!this.isRefreshing) {
      this.isRefreshing = true;

    return this.authService.refreshToken().pipe(
      switchMap((res) => {
        this.isRefreshing = false;

        this.authService.saveToken(res.token, res.refreshToken);
        const newRequest = this.addTokenToRequest(request, res.token);
        return next.handle(newRequest);
      }),
      catchError((error) => {
        this.isRefreshing = false;
        this.authService.logout()
        return throwError(() => error);
      })
    );
    }
    return next.handle(request);
  }
}
