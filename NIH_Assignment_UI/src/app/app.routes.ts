import { Routes } from '@angular/router';
import { LoginComponent } from './core/auth/components/login/login.component';
import { AdminGuard } from './core/auth/gaurds/admin.guard';

export const appRoutes: Routes = [
    { path: 'login', component: LoginComponent },
    {
        path: 'students',
        loadChildren: () =>
            import('./features/students/students.routes').then(m => m.STUDENT_ROUTES),
        canActivate: [AdminGuard]
    },
    {
        path: 'courses',
        loadChildren: () =>
            import('./features/cources/cources.routes').then(m => m.COURSE_ROUTES),
        canActivate: [AdminGuard]
    },
    {
        path: 'enrollments',
        loadChildren: () =>
            import('./features/enrollment/enrollment.routes').then(m => m.ENROLLMENT_ROUTES)
    },
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: '**', redirectTo: '/login' }
];
