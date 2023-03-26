package com.practica.hoteles.reservas.entities;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

/**

 Clase que representa una reserva en un hotel.

 Esta clase contiene información sobre la reserva, como el hotel en el que se

 realizará la reserva, la fecha de inicio y fin de la reserva, y el correo electrónico

 del cliente que realiza la reserva.

 */
@Entity
@Table(name = "bookings")
@Data
public class Booking {

    /**

     Identificador único de la reserva.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    /**

     Hotel en el que se realizará la reserva.
     */
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    /**

     Fecha de inicio de la reserva.
     */
    private LocalDate dateFrom;
    /**

     Fecha de fin de la reserva.
     */
    private LocalDate dateTo;
    /**

     Correo electrónico del cliente que realiza la reserva.
     */
    private String email;
    /**

     Constructor por defecto.
     */
    public Booking() {
    }
    /**

     Constructor con parámetros.
     @param hotel Hotel en el que se realizará la reserva.
     @param dateFrom Fecha de inicio de la reserva.
     @param dateTo Fecha de fin de la reserva.
     @param email Correo electrónico del cliente que realiza la reserva.
     */
    public Booking(Hotel hotel, LocalDate dateFrom, LocalDate dateTo, String email) {
        this.hotel = hotel;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.email = email;
    }
}
