import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable, shareReplay, tap } from 'rxjs';
import { Student, StudentRequest } from '../models/student.model';
import { EMPTY_PAGE, Page } from '../../../core/models/page.model';

@Injectable({ providedIn: 'root' })
export class StudentService {
    private studentBaseUrl = '/students';
    private studentsSubject = new BehaviorSubject<Student[]>([]);
    students$ = this.studentsSubject.asObservable();

    constructor(private http: HttpClient) { }

    getStudents(page = 0, size = 20, sortBy = 'lastName', sortOrder = 'asc'): Observable<Page<Student>> {
        return this.http.get<any>(`${this.studentBaseUrl}?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}`)
            .pipe(tap(res => this.studentsSubject.next(res.content)));

    }

    getById(id: number): Observable<Student> {
        return this.http.get<Student>(`${this.studentBaseUrl}/${id}`);
    }

    create(student: StudentRequest): Observable<Student> {
        return this.http.post<Student>(this.studentBaseUrl, student).pipe(
            tap(newStudent => this.studentsSubject.next([...this.studentsSubject.value, newStudent]))
        );
    }

    update(id: number, student: StudentRequest): Observable<Student> {
        return this.http.put<Student>(`${this.studentBaseUrl}/${id}`, student).pipe(
            tap(updated => {
                const updatedList = this.studentsSubject.value.map(s => s.id === id ? updated : s);
                this.studentsSubject.next(updatedList);
            })
        );
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.studentBaseUrl}/${id}`).pipe(
            tap(() => {
                const filtered = this.studentsSubject.value.filter(s => s.id !== id);
                this.studentsSubject.next(filtered);
            })
        );
    }

    search(searchString: string, page = 0, size = 20): Observable<any> {
        return this.http.get<any>(`${this.studentBaseUrl}/search?searchString=${searchString}&page=${page}&size=${size}`)
            .pipe(tap(res => this.studentsSubject.next(res.content)));
    }
}
