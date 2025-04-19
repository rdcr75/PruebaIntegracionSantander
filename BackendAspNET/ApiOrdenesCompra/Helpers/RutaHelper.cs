using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiOrdenesCompra.Helpers
{
    public class RutaHelper
    {
        public static string ObtenerRutaArchivoEntrada()
        {
            string basePath = AppContext.BaseDirectory;
            DirectoryInfo dir = new DirectoryInfo(basePath);

            DirectoryInfo solutionRoot = dir.Parent.Parent;

            if (solutionRoot == null)
                throw new DirectoryNotFoundException("No se pudo determinar la ruta base del proyecto.");

            string ruta = Path.Combine(
                solutionRoot.FullName,
                "ApacheCamel",
                "camel-integration",
                "data",
                "in",
                "orden_" + DateTime.Now.ToString("yyyyMMdd_HHmmss") + ".json"
            );

            return ruta;
        }
    }
}