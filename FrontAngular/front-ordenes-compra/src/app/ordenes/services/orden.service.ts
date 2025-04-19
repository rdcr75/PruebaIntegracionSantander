import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrdenCompra } from '../models/orden-compra';

@Injectable({
  providedIn: 'root'
})
  
export class OrdenService {
  private apiUrl = 'https://localhost:44376/ordenes'; // Ajustar si cambia

  constructor(private http: HttpClient) { }

  // guardarOrden(orden: OrdenCompra): Observable<any> {
  //   return this.http.post(this.apiUrl, orden);
  // }

  listar(): Observable<OrdenCompra[]> {
    return this.http.get<OrdenCompra[]>(this.apiUrl);
  }
}
