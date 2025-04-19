import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'ordenes',
        loadChildren: () =>
            import('./ordenes/ordenes.routes').then((m) => m.ORDENES_ROUTES),
    },
    {
        path: '',
        redirectTo: 'ordenes',
        pathMatch: 'full',
    },
];
