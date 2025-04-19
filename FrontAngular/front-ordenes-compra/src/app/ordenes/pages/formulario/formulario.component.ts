import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { OrdenService } from '../../services/orden.service';
import { OrdenCompra } from '../../models/orden-compra';

@Component({
  standalone: true,
  selector: 'app-formulario',
  templateUrl: './formulario.component.html',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
})
export class FormularioComponent implements OnInit {
  formulario: FormGroup;

  productos = [
    { nombre: 'Laptop', precio: 1200 },
    { nombre: 'Teclado', precio: 100 },
    { nombre: 'Mouse', precio: 50 },
  ];

  precioSeleccionado: number = 0;
  total: number = 0;

  constructor(private fb: FormBuilder, private ordenService: OrdenService, private snackBar: MatSnackBar) {
    this.formulario = this.fb.group({
      cliente: ['', Validators.required],
      producto: [this.productos[0].nombre, Validators.required],
      cantidad: [1, Validators.required],
    });

    this.actualizarPrecioYTotal(); // Establece el precio inicial
  }

  ngOnInit() {
    console.log()
    this.formulario.get('producto')?.valueChanges.subscribe(nombreProducto => {
      const producto = this.productos.find(p => p.nombre === nombreProducto);
      this.precioSeleccionado = producto ? producto.precio : 0;
      this.calcularTotal();
    });

    this.formulario.get('cantidad')?.valueChanges.subscribe(() => {
      this.calcularTotal();
    });
  }

  calcularTotal() {
    const cantidad = this.formulario.get('cantidad')?.value || 0;
    this.total = this.precioSeleccionado * cantidad;
  }

  actualizarPrecioYTotal() {
    const productoSeleccionado = this.formulario.get('producto')?.value;
    const producto = this.productos.find(p => p.nombre === productoSeleccionado);
    this.precioSeleccionado = producto ? producto.precio : 0;
    this.actualizarTotal();
  }

  actualizarTotal() {
    const cantidad = this.formulario.get('cantidad')?.value || 0;
    this.total = this.precioSeleccionado * cantidad;
  }


  // guardar() {
  //   if (this.formulario.valid) {
  //     const datos: OrdenCompra = {
  //       cliente: this.formulario.get('cliente')?.value,
  //       producto: this.formulario.get('producto')?.value,
  //       cantidad: this.formulario.get('cantidad')?.value,
  //       precioUnitario: this.precioSeleccionado,
  //       total: this.total
  //     };

  //     this.ordenService.guardarOrden(datos).subscribe({
  //       next: () => {
  //         this.snackBar.open('Orden enviada con éxito 🎉', 'Cerrar', {
  //           duration: 3000,
  //           horizontalPosition: 'right',
  //           verticalPosition: 'top'
  //         });

  //         this.formulario.reset({
  //           cliente: '',
  //           producto: '',
  //           cantidad: 1
  //         });

  //         this.precioSeleccionado = 0;
  //         this.total = 0;
  //       },
  //       error: (err: any) => {
  //         console.error('Error al guardar la orden:', err);
  //         this.snackBar.open('Error al enviar la orden ❌', 'Cerrar', {
  //           duration: 3000,
  //           horizontalPosition: 'right',
  //           verticalPosition: 'top'
  //         });
  //       }
  //     });
  //   } else {
  //     console.log('Formulario inválido');
  //   }
  // }
}
