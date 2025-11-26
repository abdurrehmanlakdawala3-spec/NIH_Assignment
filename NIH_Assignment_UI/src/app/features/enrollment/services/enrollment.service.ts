import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { EnrollmentCreateRequest, EnrollmentView } from '../models/enrollment.model';
import { Page } from 'src/app/core/models/page.model';

@Injectable({ providedIn: 'root' })
export class EnrollmentService {
  private enrollmentBaseUrl = '/enrollments';
  private enrollmentsSubject = new BehaviorSubject<EnrollmentView[]>([]);
  enrollments$ = this.enrollmentsSubject.asObservable();

  constructor(private http: HttpClient) {}

  getEnrollments(page = 0, size = 20, searchText?: string): Observable<Page<EnrollmentView>> {
    let query = `?page=${page}&size=${size}`;
    if (searchText) query += `&searchText=${searchText}`;
    return this.http.get<any>(`${this.enrollmentBaseUrl}${query}`)
      .pipe(tap(res => this.enrollmentsSubject.next(res.content)));
  }

  getById(id: number): Observable<EnrollmentView> {
    return this.http.get<EnrollmentView>(`${this.enrollmentBaseUrl}/${id}`);
  }

  create(enrollment: EnrollmentCreateRequest): Observable<EnrollmentView> {
    return this.http.post<EnrollmentCreateRequest>(this.enrollmentBaseUrl, enrollment).pipe(
      tap(newEnrollment => this.enrollmentsSubject.next([...this.enrollmentsSubject.value, newEnrollment]))
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.enrollmentBaseUrl}/${id}`).pipe(
      tap(() => {
        const filtered = this.enrollmentsSubject.value.filter(e => e.id !== id);
        this.enrollmentsSubject.next(filtered);
      })
    );
  }
}
