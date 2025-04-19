import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrdenService } from './ordenes/services/orden.service';
import { OrdenCompra } from './ordenes/models/orden-compra';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: true,
  imports: [CommonModule, FormsModule],
})
export class AppComponent {
  title = 'front-ordenes-compra';
  ordenes: OrdenCompra[] = [];

  private ordenService = inject(OrdenService);

  constructor(private http: HttpClient) {
    this.cargarOrdenes();
  }

  cargarOrdenes() {
    this.ordenService.listar().subscribe({
      next: (data) => {
        this.ordenes = data;
        console.log('Órdenes recibidas:', data);
      },
      error: (err) => console.error('Error al obtener órdenes:', err)
    });
  }

  mostrarFormulario: boolean = false;

  nuevaOrden: any = {
    Cliente: '',
    Fecha: '',
    Productos: []
  };

  nuevoProducto: any = {
    Nombre: '',
    Cantidad: 1
  };

  agregarProducto() {
    this.nuevaOrden.Productos.push({ ...this.nuevoProducto });
    this.nuevoProducto = { Nombre: '', Cantidad: 1 };
  }

  crearOrden() {
    this.http.post<any>('https://localhost:44376/ordenes', this.nuevaOrden).subscribe({
      next: (data: any) => {
        console.log('Orden creada:', data);
        this.ordenes.push(data); // actualizar la lista
        this.nuevaOrden = { Cliente: '', Fecha: '', Productos: [] };
        this.mostrarFormulario = false;
        this.cargarOrdenes();
      },
      error: (error: any) => {
        console.error('Error al crear orden:', error);
      }
    });
  }
}