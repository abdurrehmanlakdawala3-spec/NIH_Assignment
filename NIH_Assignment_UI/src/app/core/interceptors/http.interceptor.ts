// src/app/core/interceptors/http.interceptor.ts
import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../auth/services/auth.service';

@Injectable()
export class ApiInterceptor implements HttpInterceptor {
  // private readonly baseUrl = 'http://localhost:8080/api/v1';
  private readonly baseUrl = '/api/v1';

  constructor(private authService: AuthService, private snackBar: MatSnackBar) {
    console.log('ApiInterceptor instantiated');
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('ApiInterceptor intercept called', req.url);

    const url = req.url.startsWith('http') ? req.url : `${this.baseUrl}${req.url.startsWith('/') ? '' : '/'}${req.url}`;
    let apiReq = req.clone({ url });

    const token = this.authService.getToken();
    if (token) {
      apiReq = apiReq.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
    }

    return next.handle(apiReq).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = 'An unexpected error occurred.';
        if (error.status === 0) {
          message = 'Cannot connect to server. Please try again later.';
        } else if (error.status === 401) {
          message = 'Session expired. Please login again.';
          this.authService.logout();
        } else if (error.status === 403) {
          message = 'You do not have permission to perform this action.';
        } else if (error.status === 404) {
          message = 'Resource not found.';
        } else if (error.error?.message) {
          message = error.error.message;
        }

        this.snackBar.open(message, 'Close', { duration: 5000 });
        return throwError(() => error);
      })
    );
  }
}
