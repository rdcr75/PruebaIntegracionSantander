import { Routes } from '@angular/router';
import { ListadoComponent } from './pages/listado/listado.component';
import { FormularioComponent } from './pages/formulario/formulario.component';

export const ORDENES_ROUTES: Routes = [
    { path: '', component: ListadoComponent },
    { path: 'nuevo', component: FormularioComponent },
    { path: 'editar/:id', component: FormularioComponent },
];

