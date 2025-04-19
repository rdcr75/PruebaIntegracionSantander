# Sistema de Gestión de Órdenes de Compra
Este sistema está compuesto por 4 proyectos integrados:

1. Frontend Angular 17
2. API Backend ASP.NET Framework 4.6.2
3. Microservicio Java con Spring Boot
4. Integración con Apache Camel

# 1. Frontend Angular 17
## Requisitos:
 * Node.js v18+
 * Angular CLI

## Instalación y ejecución:
```bash
cd FrontAngular/front-ordenes-compra
npm install
ng serve
```
El frontend se ejecuta en: http://localhost:4200

## Configuración:
En el archivo app.component.ts, el endpoint de la API debe apuntar a:

```ts
this.http.post<any>('https://localhost:44376/ordenes', this.nuevaOrden)
```

# 2. API Backend ASP.NET Framework 4.6.2
## Requisitos:
 - Visual Studio 2019 o superior
 - .NET Framework 4.6.2

## Configuración:
 - Asegúrate de que la API tenga habilitado CORS si se consume desde Angular.
 - Debe exponer el endpoint POST /ordenes para recibir órdenes de compra.

## Ejecución:
Compila y ejecuta desde Visual Studio. (URL: http://localhost:44376)

# 3. Microservicio Java con Spring Boot
## Requisitos:
- Java 17+
- Maven 3.6+
- IDE: IntelliJ IDEA / Eclipse / Visual Studio Code

## Ejecución:
```bash
./mvnw spring-boot:run
```
Debe exponerse en: http://localhost:8003/procesar-orden

Este microservicio recibe una orden, la procesa y puede devolver una respuesta con validación o confirmación de procesamiento.

# 4. Apache Camel + Spring Boot (Integrador)
## Requisitos:
 - Java 17+
 - Maven
 - Spring Boot

## Funcionalidad:
 - Monitorea archivos JSON en data/in/
 - Envía el JSON procesado al microservicio
 - En caso de error, lo guarda en data/errors/
 - En caso de éxito, guarda un archivo en data/procesados/ con:
   - JSON original
   - JSON transformado
   - Respuesta del microservicio

## Estructura esperada del archivo:
```json
{
  "archivoOriginal": {...},
  "archivoTransformado": {...},
  "respuestaMicroservicio": {...}
}
```

## Ejecución:
```bash
./mvnw spring-boot:run
```
## Estructura de carpetas esperada:
```go
data/
├── in/             ← Carpeta donde se depositan archivos JSON de entrada
├── errors/         ← Archivos con error en procesamiento
└── procesados/     ← Archivos procesados correctamente
```
Debe exponerse en: http://localhost:8001

# Flujo de Datos
1. Usuario crea una orden desde Angular. 
2. Angular la envía a la API .NET (/ordenes).
3. La API guarda un archivo JSON en data/in/. 
4. Apache Camel detecta el archivo, lo transforma y lo envía al microservicio Java.
5. La respuesta del microservicio es guardada junto al JSON original y transformado en data/procesados/.

# Pruebas de Backend

Usar la colección de postman ubicada en el directorio raís del proyecto, en el siguiente link:
https://github.com/rdcr75/PruebaIntegracionSantander/blob/master/ProyectoIntegracion.postman_collection.json

# Autor
Desarrollado por Ruben Cabrera.
