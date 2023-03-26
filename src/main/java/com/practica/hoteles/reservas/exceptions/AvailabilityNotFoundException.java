package com.practica.hoteles.reservas.exceptions;

public class AvailabilityNotFoundException extends Exception {
    public AvailabilityNotFoundException(Long id) {
        super(id + " La disponibilidad no existe ");
    }
}
