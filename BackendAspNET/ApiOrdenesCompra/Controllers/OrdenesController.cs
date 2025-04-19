using System;
using System.Collections.Generic;
using System.IO;
using System.Web.Http;
using ApiOrdenesCompra.Models;
using Newtonsoft.Json;
using ApiOrdenesCompra.Helpers;


namespace ApiOrdenesCompra.Controllers
{
    public class OrdenesController : ApiController
    {
        private static List<OrdenCompra> ordenes = new List<OrdenCompra>();

        [HttpGet]
        [Route("ordenes")]
        public IHttpActionResult Get()
        {
            return Ok(ordenes);
        }

        [HttpPost]
        [Route("ordenes")]
        public IHttpActionResult Post(OrdenCompra orden)
        {
            if (!ModelState.IsValid)
                return BadRequest("Datos inválidos.");

            ordenes.Add(orden);

            var json = JsonConvert.SerializeObject(orden, Formatting.Indented);
            var basePath = AppContext.BaseDirectory;
            var ruta = RutaHelper.ObtenerRutaArchivoEntrada();

            System.IO.File.WriteAllText(ruta, json);

            return Ok(new { mensaje = "Orden registrada correctamente" });
        }
    }
}