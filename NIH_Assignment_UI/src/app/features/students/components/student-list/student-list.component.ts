import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { StudentService } from '../../services/student.service';
import { Student } from '../../models/student.model';
import { ModalConfirmationComponent } from '../../../../shared/model-confirmation.component';
import { PaginationComponent } from '../../../../shared/pagination.component';
import { SearchBarComponent } from '../../../../shared/search-bar.component';
import { RouterModule, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-student-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
    PaginationComponent,
    SearchBarComponent,
    RouterModule,
  ],
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.scss']
})
export class StudentListComponent implements OnInit {
  students: Student[] = [];
  displayedColumns = ['firstName', 'lastName', 'email', 'actions'];
  totalElements = 0;
  pageSize = 5;
  pageIndex = 0;

  constructor(
    private studentService: StudentService, 
    private router: Router,
    private dialog: MatDialog,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadStudents();
    this.studentService.students$.subscribe(students => {
        this.students = students
        this.cdr.detectChanges();
    });
  }

  loadStudents() {
    this.studentService.getStudents(this.pageIndex, this.pageSize).subscribe(res => {
      this.totalElements = res.totalElements;
      this.cdr.detectChanges();
    });
  }

  onSearch(value: string) {
    if (value.trim()) {
      this.studentService.search(value, 0, this.pageSize).subscribe(res => {
        this.totalElements = res.totalElements;
        this.pageIndex = 0;
        this.cdr.detectChanges();
      });
    } else {
      this.loadStudents();
    }
  }

  onPageChange(event: any) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadStudents();
  }

  addStudent() {
    this.router.navigate(['/students/add']);
  }

  editStudent(id?: number) {
    if (id) this.router.navigate([`/students/edit/${id}`]);
  }

confirmDelete(student: Student) {
  const dialogRef = this.dialog.open(ModalConfirmationComponent, {
    width: '400px',
    data: {
      title: 'Delete Student',
      message: 'Are you sure you want to delete this student?'
    }
  });

  dialogRef.componentInstance.confirmed.subscribe(() => {
    this.deleteStudent(student);
    dialogRef.close();
  });

  dialogRef.componentInstance.cancelled.subscribe(() => {
    dialogRef.close();
  });
}


  deleteStudent(student: Student) {
    if (student?.id) {
      this.studentService.delete(student.id).subscribe(() => {
        this.loadStudents()
      });
    }
  }
}
