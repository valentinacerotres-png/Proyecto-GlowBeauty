package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.peristence.*;

import javax.annotation.processing.Generated;

@Entity
@Table(name = "productos")
public class Producto {

    @JsonTypeId
    @Generated(strategy = Generated.IDENTITY)
    private Long id;

    private String nombre;
    private String categoria;
    private Double precio;
    private Integer stock;

    public Producto() {}

    public Producto(String nombre, String categoria, Double precio, Integer stock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getCategoria() {return categoria;}
    public void setCategoria(String categoria) {this.categoria = categoria;}

    public Double getPrecio() {return precio;}
    public void setPrecio(Double precio) {this.precio = precio;}

    public Integer getStock() {return stock;}
    public void setStock(Integer stock) {this.stock = stock;}

}
