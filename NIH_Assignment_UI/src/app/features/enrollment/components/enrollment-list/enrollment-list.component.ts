import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { EnrollmentService } from '../../services/enrollment.service';
import { EnrollmentView, EnrollmentCreateRequest } from '../../models/enrollment.model';
import { ModalConfirmationComponent } from '../../../../shared/model-confirmation.component';
import { PaginationComponent } from '../../../../shared/pagination.component';
import { SearchBarComponent } from '../../../../shared/search-bar.component';
import { Router, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';


@Component({
  standalone: true,
  selector: 'app-enrollment-list',
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    MatIconModule,
    RouterModule,
        PaginationComponent,
        SearchBarComponent,
  ],
  templateUrl: './enrollment-list.component.html',
  styleUrls: ['./enrollment-list.component.scss']
})
export class EnrollmentListComponent implements OnInit {

  displayedColumns = ['studentFullName', 'courseCode', 'courseTitle', 'actions'];
  enrollments: EnrollmentView[] = [];
  pageIndex = 0;
  pageSize = 5;
  totalElements = 0;
  searchText = '';

  constructor(
    private service: EnrollmentService,
    private cdr: ChangeDetectorRef,
    private router: Router,
    private dialog: MatDialog,
) {}

  ngOnInit() {
    this.loadData();
    this.service.enrollments$.subscribe(enrollemnts => {
        this.enrollments = enrollemnts;
        this.cdr.detectChanges();
    })
  }

  loadData() {
    this.service.getEnrollments(this.pageIndex, this.pageSize, this.searchText)
      .subscribe(res => {
        this.totalElements = res.totalElements;
        this.pageIndex = 0;
        this.cdr.detectChanges();
      });
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadData();
  }

  onSearch(value: string) {
    console.log(value + "123123123123123")
    this.searchText = value.trim();
    this.loadData();
  }

  
    confirmDelete(enrollment: EnrollmentView) {
  const dialogRef = this.dialog.open(ModalConfirmationComponent, {
    width: '400px',
    data: {
      title: 'Delete Enrollment',
      message: 'Are you sure you want to delete this enrollment?'
    }
  });

  dialogRef.componentInstance.confirmed.subscribe(() => {
    this.deleteEnrollment(enrollment);
    dialogRef.close();
  });

  dialogRef.componentInstance.cancelled.subscribe(() => {
    dialogRef.close();
  });
}

  addEnrollment() {
    this.router.navigate(['/enrollments/add']);
  }

      deleteEnrollment(enrollment:EnrollmentView) {
    if (enrollment?.id) {
      this.service.delete(enrollment.id).subscribe(() => {
        this.loadData();
      });
    }
  }
}
