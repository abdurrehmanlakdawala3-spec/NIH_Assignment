import { Routes } from '@angular/router';
import { CourseListComponent } from './components/course-list/course-list.component';
import { CourseFormComponent } from './components/course-form/course-form.component';

export const COURSE_ROUTES: Routes = [
  { path: '', component: CourseListComponent },
  { path: 'new', component: CourseFormComponent },
  { path: 'edit/:id', component: CourseFormComponent }
];
