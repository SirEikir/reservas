package com.practica.hoteles.reservas.exceptions;

public class NotAvailableException extends Exception{
    public NotAvailableException(long id) {
        super(id + " No existe disponibilidad en esas fechas");
    }
}
