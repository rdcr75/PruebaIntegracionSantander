package com.ruben.ordenes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.ordenes.model.Orden;
import com.ruben.ordenes.service.OrdenService;

import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/procesar-orden")
@Validated
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @PostMapping
    public ResponseEntity<String> procesarOrden(@RequestBody @Valid Orden orden) {
        ordenService.procesarOrden(orden);
        return ResponseEntity.ok("Orden procesada con éxito.");
    }
}
