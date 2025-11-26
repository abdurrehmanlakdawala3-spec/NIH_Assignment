import { CommonModule } from "@angular/common";
import { ChangeDetectorRef, Component, OnInit } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatPaginatorModule, PageEvent } from "@angular/material/paginator";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { Router, RouterModule } from "@angular/router";
import { Course } from "../../models/course.model";
import { CourseService } from "../../services/course.service";
import { MatIconModule } from "@angular/material/icon";
import { MatDialogModule } from "@angular/material/dialog";
import { ModalConfirmationComponent } from "src/app/shared/model-confirmation.component";
import { PaginationComponent } from "../../../../shared/pagination.component";
import { MatDialog } from '@angular/material/dialog';
import { SearchBarComponent } from '../../../../shared/search-bar.component';

@Component({
  standalone: true,
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.scss'],
  imports: [
    MatTableModule,
    MatPaginatorModule,
    MatButtonModule,
    RouterModule,
    CommonModule,
    MatIconModule,
    MatDialogModule,
    PaginationComponent,
    SearchBarComponent,
    RouterModule
  ]
})
export class CourseListComponent implements OnInit {

  displayedColumns = ['id', 'courseName', 'courseDescription', 'courseCode', 'actions'];
  dataSource: Course[] = []
  totalElements = 0;
  pageSize = 5;
  pageIndex = 0;

  constructor(
    private service: CourseService,
    private router: Router,
    private cdr: ChangeDetectorRef,
    private dialog: MatDialog,
  ) {}

  ngOnInit() {
    this.loadCourses();
    this.service.courses$.subscribe(course => {
        this.dataSource = course;
        this.cdr.detectChanges();
    })
  }

  loadCourses() {
    this.service.getCourses(this.pageIndex, this.pageSize).subscribe(res => {
      this.totalElements = res.totalElements;
      this.cdr.detectChanges();
    });
  }

  
  onPageChange(event: any) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadCourses();
  }

  edit(id:number){
    this.router.navigate(['courses/edit', id]);
  }

    onSearch(value: string) {
    if (value.trim()) {
      this.service.search(value, 0, this.pageSize).subscribe(res => {
        this.totalElements = res.totalElements;
        this.pageIndex = 0;
        this.cdr.detectChanges();
      });
    } else {
      this.loadCourses();
    }
  }


    confirmDelete(course: Course) {
  const dialogRef = this.dialog.open(ModalConfirmationComponent, {
    width: '400px',
    data: {
      title: 'Delete Course',
      message: 'Are you sure you want to delete this clourse?'
    }
  });

  dialogRef.componentInstance.confirmed.subscribe(() => {
    this.deleteCourse(course);
    dialogRef.close();
  });

  dialogRef.componentInstance.cancelled.subscribe(() => {
    dialogRef.close();
  });
}

  
  deleteCourse(course: Course) {
    if (course?.id) {
      this.service.delete(course.id).subscribe(() => {
        this.loadCourses();
      });
    }
  }

}
