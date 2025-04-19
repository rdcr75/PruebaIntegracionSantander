export interface Producto {
    Nombre: string;
    Cantidad: number;
}

export interface OrdenCompra {
    Cliente: string;
    Fecha: string;
    Productos: Producto[];
}
