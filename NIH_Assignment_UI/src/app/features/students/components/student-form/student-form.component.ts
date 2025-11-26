import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { Router, ActivatedRoute } from '@angular/router';
import { StudentService } from '../../services/student.service';
import { StudentRequest } from 'src/app/features/students/models/student.model';

@Component({
  selector: 'app-student-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './student-form.component.html'
})
export class StudentFormComponent implements OnInit {
  studentForm = this.fb.group({
    firstName: ['', [Validators.required, Validators.maxLength(100)]],
    lastName: ['', [Validators.required, Validators.maxLength(100)]],
    email: ['', [Validators.required, Validators.email, Validators.maxLength(255)]],
    dateOfBirth: ['']
  });
  isEdit = false;
  studentId?: number;

  constructor(
    private fb: FormBuilder,
    private studentService: StudentService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEdit = true;
        this.studentId = +params['id'];
        this.studentService.getById(this.studentId).subscribe(student => {
          this.studentForm.patchValue(student);
        });
      }
    });
  }

  onSubmit() {
    if (this.studentForm.valid) {
    const formValue = this.studentForm.value;
        const student: StudentRequest = {
        firstName: formValue.firstName ?? '',
        lastName: formValue.lastName ?? '',
        email: formValue.email ?? '',
        dateOfBirth: formValue.dateOfBirth || undefined // optional
    };
      if (this.isEdit && this.studentId) {
        this.studentService.update(this.studentId, student).subscribe(() => this.router.navigate(['/students']));
      } else {
        this.studentService.create(student).subscribe(() => this.router.navigate(['/students']));
      }
    }
  }
}
