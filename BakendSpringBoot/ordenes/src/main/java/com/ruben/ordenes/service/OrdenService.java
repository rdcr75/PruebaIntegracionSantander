package com.ruben.ordenes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ruben.ordenes.model.Orden;
import com.ruben.ordenes.util.LogUtil;

@Service
public class OrdenService {
    private static final Logger logger = LoggerFactory.getLogger(OrdenService.class);

    public void procesarOrden(Orden orden) {
        // Imprimir por consola
        logger.info("Procesando orden...");
        logger.info("Cliente: " + orden.getCliente());
        logger.info("Fecha: " + orden.getFecha());
        logger.info("Total productos: " + orden.getTotalProductos());

        // Log de procesamiento en archivo JSON
        LogUtil.guardarLog(orden);
    }
}
