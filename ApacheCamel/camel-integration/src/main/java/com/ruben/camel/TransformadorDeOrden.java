package com.ruben.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class TransformadorDeOrden implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(TransformadorDeOrden.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);
        logger.info("Recibiendo archivo JSON: {}", body);

        JsonNode originalJson = mapper.readTree(body);

        String cliente = originalJson.path("Cliente").asText("");
        JsonNode productos = originalJson.path("Productos");
        String fechaTexto = originalJson.path("Fecha").asText("");

        if (cliente.isEmpty() || cliente.length() < 3 || cliente.length() > 100) {
            throw new IllegalArgumentException("El nombre del cliente es inválido.");
        }
        if (!fechaTexto.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$")) {
            throw new IllegalArgumentException("La fecha no tiene el formato esperado yyyy-MM-ddTHH:mm:ss.");
        }

        try {
            LocalDateTime.parse(fechaTexto, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La fecha es inválida: " + fechaTexto);
        }

        if (!productos.isArray() || productos.size() == 0) {
            throw new IllegalArgumentException("La lista de productos está vacía.");
        }

        ArrayNode productosArray = mapper.createArrayNode();
        int totalProductos = 0;

        for (JsonNode producto : productos) {
            String nombre = producto.path("Nombre").asText("");
            int cantidad = producto.path("Cantidad").asInt(-1);
            if (cantidad <= 0 || nombre.isEmpty()) {
                throw new IllegalArgumentException("Producto inválido: " + producto);
            }
            totalProductos += cantidad;

            ObjectNode productoNode = mapper.createObjectNode();
            productoNode.put("Nombre", nombre);
            productoNode.put("Cantidad", cantidad);
            productosArray.add(productoNode);
        }

        if (totalProductos <= 0) {
            throw new IllegalArgumentException("Total de productos inválido.");
        }

        // ⚠️ AQUÍ VIENE LA CORRECCIÓN
        ObjectNode nuevoJson = mapper.createObjectNode();
        nuevoJson.put("Cliente", cliente);
        nuevoJson.put("Fecha", fechaTexto); // Usa fecha tal cual, ya viene en formato correcto
        nuevoJson.set("Productos", productosArray); // <-- Aquí agregas el array
        nuevoJson.put("TotalProductos", totalProductos);

        String nuevoJsonStr = mapper.writeValueAsString(nuevoJson);

        logger.info("Transformación completada: {}", nuevoJsonStr);
        exchange.getIn().setBody(nuevoJsonStr);
    }

}
