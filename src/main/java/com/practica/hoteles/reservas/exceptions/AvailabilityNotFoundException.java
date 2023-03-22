package com.practica.hoteles.reservas.exceptions;

public class AvailabilityNotFoundException extends RuntimeException {
    public AvailabilityNotFoundException(Long id) {
        super(id + "No existe disponibilidad para esos parametros ");
    }
}
