package com.practica.hoteles.reservas.entities;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

/**

 Clase que representa la disponibilidad de habitaciones en un hotel para una fecha específica.

 Esta clase contiene información sobre la fecha de disponibilidad, el hotel en el que se ofrece,

 el número de habitaciones disponibles para la reserva en esa fecha específica.

 */
@Entity
@Table(name = "availabilities")
@Data
public class Availability {

    /**

     Identificador único de la disponibilidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**

     Fecha de disponibilidad.
     */
    private LocalDate date;
    /**

     Hotel en el que se ofrece la disponibilidad.
     */
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    /**

     Número de habitaciones disponibles para reserva.
     */
    private int rooms;
    /**

     Constructor por defecto.
     */
    public Availability() {
    }
    /**

     Constructor con parámetros.
     @param date Fecha de disponibilidad.
     @param hotel Hotel en el que se ofrece la disponibilidad.
     @param rooms Número de habitaciones disponibles para reserva.
     */
    public Availability(LocalDate date, Hotel hotel, int rooms) {
        this.date = date;
        this.hotel = hotel;
        this.rooms = rooms;
    }
}
