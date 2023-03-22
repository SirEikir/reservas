package com.practica.hoteles.reservas.entities;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private String email;

    public Booking() {
    }

    public Booking(Hotel hotel, LocalDate dateFrom, LocalDate dateTo, String email) {
        this.hotel = hotel;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.email = email;
    }
}
