import { Routes } from '@angular/router';
import { EnrollmentListComponent } from './components/enrollment-list/enrollment-list.component';
import { EnrollmentFormComponent } from './components/enrollment-form/enrollment-form.component';
import { MyEnrollmentComponent } from './components/my-enrollments/my-entollments.component';
import { AdminGuard } from 'src/app/core/auth/gaurds/admin.guard';
import { StudentGuard } from 'src/app/core/auth/gaurds/student.guard';

export const ENROLLMENT_ROUTES: Routes = [
  { path: '', component: EnrollmentListComponent, canActivate: [AdminGuard]},
  { path: 'add', component: EnrollmentFormComponent, canActivate: [AdminGuard] },
  { path: 'my', component: MyEnrollmentComponent, canActivate: [StudentGuard] },
];
