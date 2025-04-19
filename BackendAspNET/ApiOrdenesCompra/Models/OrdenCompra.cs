using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiOrdenesCompra.Models
{
    public class OrdenCompra
    {
        public string Cliente { get; set; }
        public List<Producto> Productos { get; set; }
        public DateTime Fecha { get; set; }
    }

    public class Producto
    {
        public string Nombre { get; set; }
        public int Cantidad { get; set; }
    }
}