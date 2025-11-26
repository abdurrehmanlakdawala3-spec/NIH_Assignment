import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Course } from '../models/course.model';

@Injectable({ providedIn: 'root' })
export class CourseService {
  private courseBaseUrl = '/courses';
  private coursesSubject = new BehaviorSubject<Course[]>([]);
  courses$ = this.coursesSubject.asObservable();

  constructor(private http: HttpClient) {}

  getCourses(page = 0, size = 20): Observable<any> {
    return this.http.get<any>(`${this.courseBaseUrl}?page=${page}&size=${size}`)
      .pipe(tap(res => this.coursesSubject.next(res.content)));
  }

  getById(id: number): Observable<Course> {
    return this.http.get<Course>(`${this.courseBaseUrl}/${id}`);
  }

  create(course: Course): Observable<Course> {
    return this.http.post<Course>(this.courseBaseUrl, course).pipe(
      tap(newCourse => this.coursesSubject.next([...this.coursesSubject.value, newCourse]))
    );
  }

  update(id: number, course: Course): Observable<Course> {
    return this.http.put<Course>(`${this.courseBaseUrl}/${id}`, course).pipe(
      tap(updated => {
        const updatedList = this.coursesSubject.value.map(c => c.id === id ? updated : c);
        this.coursesSubject.next(updatedList);
      })
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.courseBaseUrl}/${id}`).pipe(
      tap(() => {
        const filtered = this.coursesSubject.value.filter(c => c.id !== id);
        this.coursesSubject.next(filtered);
      })
    );
  }

  isCourseCodeAvailable(code: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.courseBaseUrl}/validate-code?courseCode=${code}`);
  }

      search(searchString: string, page = 0, size = 20): Observable<any> {
        return this.http.get<any>(`${this.courseBaseUrl}/search?searchString=${searchString}&page=${page}&size=${size}`)
            .pipe(tap(res => this.coursesSubject.next(res.content)));
    }
}
