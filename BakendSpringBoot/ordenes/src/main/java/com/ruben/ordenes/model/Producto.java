package com.ruben.ordenes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;

public class Producto {

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @JsonProperty("Nombre")
    private String nombre;

    @Min(value = 1, message = "La Cantidad de productos debe ser mayor a 0")
    @JsonProperty("Cantidad")
    private int cantidad;

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
