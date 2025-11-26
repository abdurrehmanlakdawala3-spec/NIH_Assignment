import { Routes } from '@angular/router';
import { StudentListComponent } from './components/student-list/student-list.component';
import { StudentFormComponent } from './components/student-form/student-form.component';

export const STUDENT_ROUTES: Routes = [
  { path: '', component: StudentListComponent },
  { path: 'add', component: StudentFormComponent },
  { path: 'edit/:id', component: StudentFormComponent }
];


// import { Routes } from '@angular/router';
// import { inject } from '@angular/core';
// import { StudentListComponent } from './student-list/student-list.component';
// import { StudentFormComponent } from './student-form/student-form.component';

// export const studentsRoutes: Routes = [
//   {
//     path: '',
//     loadComponent: () =>
//       import('./student-list/student-list.component').then(m => m.StudentListComponent)
//   },
//   {
//     path: 'add',
//     loadComponent: () =>
//       import('./student-form/student-form.component').then(m => m.StudentFormComponent)
//   },
//   {
//     path: 'edit/:id',
//     loadComponent: () =>
//       import('./student-form/student-form.component').then(m => m.StudentFormComponent)
//   }
// ];
