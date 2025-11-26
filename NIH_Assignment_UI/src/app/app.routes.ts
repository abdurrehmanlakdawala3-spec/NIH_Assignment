import { Routes } from '@angular/router';

export const appRoutes: Routes = [
    {
        path: 'students',
        loadChildren: () =>
            import('./features/students/students.routes').then(m => m.STUDENT_ROUTES)
    },
    {
        path: 'courses',
        loadChildren: () =>
            import('./features/cources/cources.routes').then(m => m.COURSE_ROUTES)
    },
    {
        path: 'enrollments',
        loadChildren: () =>
            import('./features/enrollment/enrollment.routes').then(m => m.ENROLLMENT_ROUTES)
    },
    { path: '', redirectTo: '/students', pathMatch: 'full' },
    { path: '**', redirectTo: '/students' }
];
