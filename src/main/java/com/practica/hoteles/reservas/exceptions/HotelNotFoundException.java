package com.practica.hoteles.reservas.exceptions;

public class HotelNotFoundException extends Exception {
    public HotelNotFoundException(Long id) {
        super("El hotel con id: " + id + " no existe.");
    }
}
