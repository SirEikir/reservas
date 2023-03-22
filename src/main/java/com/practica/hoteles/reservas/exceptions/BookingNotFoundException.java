package com.practica.hoteles.reservas.exceptions;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(Long id) {
        super("La reserva con el id: " + id + " no existe.");
    }
}
