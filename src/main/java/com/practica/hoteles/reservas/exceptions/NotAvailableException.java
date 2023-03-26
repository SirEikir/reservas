package com.practica.hoteles.reservas.exceptions;

public class NotAvailableException extends RuntimeException{
    public NotAvailableException(Long id) {
        super(id + "No existe disponibilidad en esas fechas");
    }
}
