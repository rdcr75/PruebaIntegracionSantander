package com.ruben.ordenes.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class Orden {

    @NotBlank(message = "El cliente no puede estar vacío")
    @JsonProperty("Cliente")
    private String cliente;

    @NotNull(message = "La fecha no puede ser nula")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("Fecha")
    private LocalDateTime fecha;

    @NotNull(message = "La lista de productos no puede ser nula")
    @Size(min = 1, message = "Debe haber al menos un producto en la orden")
    @Valid
    @JsonProperty("Productos")
    private List<Producto> productos;

    @JsonProperty("TotalProductos")
    private int totalProductos;

    // Getters y Setters
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public int getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(int totalProductos) {
        this.totalProductos = totalProductos;
    }
}
