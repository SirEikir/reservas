package com.practica.hoteles.reservas.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "hotels")
@Data
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    public Hotel() {
    }

    public Hotel(String name, String category) {
        this.name = name;
        this.category = category;
    }
}
