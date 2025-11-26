// src/app/features/enrollments/enrollment-form.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { Router } from '@angular/router';
import { Observable, of, startWith, map, switchMap, filter } from 'rxjs';
import { StudentService } from 'src/app/features/students/services/student.service';
import { CourseService } from 'src/app/features/cources/services/course.service';
import { EnrollmentService } from '../../services/enrollment.service';
import { EnrollmentCreateRequest } from '../../models/enrollment.model';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-enrollment-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatAutocompleteModule,
    MatCardModule,
  ],
  templateUrl: './enrollment-form.component.html',
  styleUrl: './enrollment-form.component.scss'
})
export class EnrollmentFormComponent implements OnInit {
  enrollmentForm = this.fb.group({
    studentSearch: ['', Validators.required],
    courseSearch: ['', Validators.required]
  });

  filteredStudents$: Observable<any[]> = of([]);
  filteredCourses$: Observable<any[]> = of([]);

  selectedStudent: any = null;
  selectedCourse: any = null;

  constructor(
    private fb: FormBuilder,
    private enrollmentService: EnrollmentService,
    private studentService: StudentService,
    private courseService: CourseService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // STUDENT TYPEAHEAD
    this.filteredStudents$ = this.enrollmentForm.get('studentSearch')!.valueChanges.pipe(
      startWith(''),
      filter(val => typeof val === 'string'), 
      switchMap(value => this.searchStudents(value))
    );

    // COURSE TYPEAHEAD
    this.filteredCourses$ = this.enrollmentForm.get('courseSearch')!.valueChanges.pipe(
      startWith(''),
      filter(val => typeof val === 'string'), 
      switchMap(value => this.searchCourses(value))
    );

        // CLEAR selection if user types manually
    this.enrollmentForm.get('studentSearch')!.valueChanges.subscribe(val => {
      if (typeof val === 'string') this.selectedStudent = null;
    });
    this.enrollmentForm.get('courseSearch')!.valueChanges.subscribe(val => {
      if (typeof val === 'string') this.selectedCourse = null;
    });
  }
  

  searchStudents(query: string | null) {
    if (!query) return of([]);
    return this.studentService.search(query).pipe(
      map(res => res.content || [])
    );
  }

  searchCourses(query: string | null) {
    if (!query) return of([]);
    return this.courseService.search(query).pipe(
      map(res => res.content || [])
    );
  }

  onStudentSelected(student: any) {
    this.selectedStudent = student;
  }

  onCourseSelected(course: any) {
    this.selectedCourse = course;
  }

    displayStudent(student: any): string {
    return student ? `${student.firstName} ${student.lastName}` : '';
  }

  displayCourse(course: any): string {
    return course ? `${course.courseCode} ${course.title}` : '';
  }

  onSubmit() {
    if (!this.selectedStudent || !this.selectedCourse) return;

    const enrollment: EnrollmentCreateRequest = {
      studentId: this.selectedStudent.id,
      courseId: this.selectedCourse.id
    };

    this.enrollmentService.create(enrollment)
      .subscribe(() => this.router.navigate(['/enrollments']));
  }
}
