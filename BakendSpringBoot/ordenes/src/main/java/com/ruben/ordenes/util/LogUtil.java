package com.ruben.ordenes.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ruben.ordenes.model.Orden;

public class LogUtil {

    private static final String LOG_DIRECTORY = "OrdenesProcesadas"; 

    public static void guardarLog(Orden orden) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // Generar timestamp actual
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        // Crear nombre de archivo con fecha y hora
        String fileName = String.format("procesamiento_orden_%s.json", timestamp);
        File outputFile = new File(LOG_DIRECTORY, fileName);

        try {
            // Crear carpeta si no existe
            outputFile.getParentFile().mkdirs();

            // Escribir archivo
            mapper.writeValue(outputFile, orden);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
