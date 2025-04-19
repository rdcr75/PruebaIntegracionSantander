import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-listado',
  imports: [CommonModule, RouterModule],
  template: `
    <h2>Listado de Órdenes de Compra</h2>
    <a routerLink="/ordenes/nuevo">Crear nueva orden</a>
  `,
})
export class ListadoComponent { }

