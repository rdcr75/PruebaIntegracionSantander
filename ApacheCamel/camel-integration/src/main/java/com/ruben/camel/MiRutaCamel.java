package com.ruben.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.springframework.stereotype.Component;

@Component
public class MiRutaCamel extends RouteBuilder {

        @Override
        public void configure() throws Exception {

                onException(Exception.class)
                                .handled(true)
                                .log(LoggingLevel.ERROR,
                                                "Ocurrió un error procesando el archivo ${header.CamelFileName}: ${exception.message}")
                                .toD("file:data/errors?fileName=${file:onlyname.noext}_error_${date:now:yyyyMMdd_HHmmss}.json");

                from("file:data/in?noop=false&include=.*\\.json")
                                .routeId("ruta-procesamiento-orden")
                                .log(LoggingLevel.INFO, "Archivo recibido: ${header.CamelFileName}")

                                .process(exchange -> {
                                        String originalJson = exchange.getIn().getBody(String.class);
                                        exchange.setProperty("jsonOriginal", originalJson);
                                })

                                .toD("log:com.ruben.camel?level=INFO&showAll=true&multiline=true")

                                .process("transformadorDeOrden")
                                .process(exchange -> {
                                        String jsonProcesado = exchange.getIn().getBody(String.class);
                                        exchange.setProperty("jsonProcesado", jsonProcesado);
                                })

                                .log(LoggingLevel.INFO, "Archivo transformado correctamente: ${body}")
                                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                                .to("http://localhost:8003/procesar-orden")

                                .process(exchange -> {
                                        String respuesta = exchange.getIn().getBody(String.class);
                                        exchange.setProperty("respuestaMicroservicio", respuesta);
                                })
                                .process(exchange -> {
                                        String original = (String) exchange.getProperty("jsonOriginal");
                                        String transformado = (String) exchange.getProperty("jsonProcesado");
                                        String respuesta = (String) exchange.getProperty("respuestaMicroservicio");

                                        String resultadoFinal = String.format("{\n" +
                                                        "  \"ArchivoOriginal\": %s,\n" +
                                                        "  \"JsonProcesado\": %s,\n" +
                                                        "  \"RespuestaMicroservicio\": \"%s\"\n" +
                                                        "}", original, transformado, respuesta.replace("\"", "\\\""));

                                        exchange.getIn().setBody(resultadoFinal);
                                })

                                .toD("file:data/procesados?fileName=${file:onlyname.noext}_procesado_${date:now:yyyyMMdd_HHmmss}.json");
        }
}