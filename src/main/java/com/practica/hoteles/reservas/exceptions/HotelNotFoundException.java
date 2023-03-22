package com.practica.hoteles.reservas.exceptions;

public class HotelNotFoundException extends RuntimeException {
    public HotelNotFoundException(Long id) {
        super("El hotel con id: " + id + " no existe.");
    }
}
