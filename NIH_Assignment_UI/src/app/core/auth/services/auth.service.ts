import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { LoginRequest, LoginResponse } from '../models/auth.model';
import { HttpClient } from '@angular/common/http';
import { Role } from '../enums/role.enum';

@Injectable({ providedIn: 'root' })
export class AuthService {
    private baseUrl = '/auth';

  private readonly TOKEN_KEY = 'auth_token';
  private readonly ROLE_KEY = 'user_role';
  private readonly ID = 'student_id';

  private userRoleSubject = new BehaviorSubject<string | null>(this.getRoleFromStorage());
  userRole$ = this.userRoleSubject.asObservable();

  constructor(private router: Router, private http: HttpClient) {}

  login(username: string, password: string) {
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, { username, password }).pipe(
      tap(res => {
        localStorage.setItem(this.TOKEN_KEY, res.token);
        localStorage.setItem(this.ROLE_KEY, res.role);
        localStorage.setItem(this.ID, String(res.studentId));
        this.userRoleSubject.next(res.role);
      })
    );
  }

  logout() {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.ROLE_KEY);
    this.userRoleSubject.next(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getRole(): string | null {
    return this.userRoleSubject.value;
  }

  isAdmin(): boolean {
    return this.getRole() === Role.ROLE_ADMIN;
  }

  isStudent(): boolean {
    return this.getRole() === Role.ROLE_STUDENT;
  }

  private getRoleFromStorage(): string | null {
    return localStorage.getItem(this.ROLE_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}


