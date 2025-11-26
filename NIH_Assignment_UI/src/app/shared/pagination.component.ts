import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-pagination',
  standalone: true,
  imports: [MatPaginatorModule, MatButtonModule],
  template: `
    <mat-paginator
      [length]="totalElements"
      [pageSize]="pageSize"
      [pageIndex]="pageIndex"
      [pageSizeOptions]="[5, 10, 20, 50]"
      (page)="pageChange.emit($event)">
    </mat-paginator>
  `
})
export class PaginationComponent {
  @Input() totalElements = 0;
  @Input() pageSize = 20;
  @Input() pageIndex = 0;
  @Output() pageChange = new EventEmitter<any>();
}
