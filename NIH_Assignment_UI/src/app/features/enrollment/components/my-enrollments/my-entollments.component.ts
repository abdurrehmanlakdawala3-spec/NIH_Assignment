import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Observable } from 'rxjs';
import { EnrollmentView } from '../../models/enrollment.model';
import { EnrollmentService } from '../../services/enrollment.service';

@Component({
  selector: 'app-my-enrollments',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatCardModule,
    MatPaginatorModule,
    MatProgressSpinnerModule
  ],
templateUrl: './my-enrollment.component.html',
  styles: [`
    mat-card {
      margin: 20px;
      padding: 20px;
    }

    .spinner-container {
      display: flex;
      justify-content: center;
      padding: 30px 0;
    }

    .no-data {
      text-align: center;
      color: gray;
      margin-top: 20px;
    }

    table {
      width: 100%;
      margin-top: 15px;
    }
  `]
})
export class MyEnrollmentComponent implements OnInit {
  enrollments: EnrollmentView[] = [];
  displayedColumns = ['courseCode', 'courseTitle'];

  constructor(private enrollmentService: EnrollmentService, 
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.fetchMyEnrollments();
  }

  fetchMyEnrollments(): void {
    this.enrollmentService.getMyEnrollments().subscribe({
      next: (page) => {
        this.enrollments = page.content;
        this.cdr.detectChanges();
      }
    });
  }
}
