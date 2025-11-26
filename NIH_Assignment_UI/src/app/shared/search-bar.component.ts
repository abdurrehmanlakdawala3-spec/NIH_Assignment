import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { debounceTime, Subject } from 'rxjs';

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <input
      type="text"
      [(ngModel)]="searchText"
      (ngModelChange)="onChange($event)"
      placeholder="Search..."
      class="search-input"
    />
  `,
  styles: [`
    .search-input { width: 100%; padding: 0.5rem; margin-bottom: 1rem; }
  `]
})
export class SearchBarComponent {
  @Output() search = new EventEmitter<string>();
  searchText = '';
  private searchSubject = new Subject<string>();

  constructor() {
    this.searchSubject.pipe(debounceTime(500)).subscribe(value => this.search.emit(value));
  }

  onChange(value: string) {
    this.searchSubject.next(value);
  }
}
