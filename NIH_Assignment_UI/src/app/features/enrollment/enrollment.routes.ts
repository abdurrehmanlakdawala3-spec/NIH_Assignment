import { Routes } from '@angular/router';
import { EnrollmentListComponent } from './components/enrollment-list/enrollment-list.component';
import { EnrollmentFormComponent } from './components/enrollment-form/enrollment-form.component';

export const ENROLLMENT_ROUTES: Routes = [
  { path: '', component: EnrollmentListComponent },
    { path: 'add', component: EnrollmentFormComponent },
];
