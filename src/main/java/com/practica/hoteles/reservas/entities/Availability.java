package com.practica.hoteles.reservas.entities;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "availabilities")
@Data
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private int rooms;

    public Availability() {
    }

    public Availability(LocalDate date, Hotel hotel, int rooms) {
        this.date = date;
        this.hotel = hotel;
        this.rooms = rooms;
    }
}
