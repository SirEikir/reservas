package com.practica.hoteles.reservas.exceptions;

public class BookingNotFoundException extends Exception {
    public BookingNotFoundException(Long id) {
        super("La reserva con el id: " + id + " no existe.");
    }
}
