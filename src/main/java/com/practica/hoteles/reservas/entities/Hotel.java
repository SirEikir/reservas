package com.practica.hoteles.reservas.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**

 Clase que representa un hotel.

 */
@Entity
@Table(name = "hotels")
@Data
public class Hotel {

    /**

     Identificador único del hotel.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**

     Nombre del hotel.
     */
    private String name;
    /**

     Categoría del hotel.
     */
    private String category;
    /**

     Constructor por defecto.
     */
    public Hotel() {
    }
    /**

     Constructor con parámetros.
     @param name Nombre del hotel.
     @param category Categoría del hotel.
     */
    public Hotel(String name, String category) {
        this.name = name;
        this.category = category;
    }
}