package com.practica.hoteles.reservas.repositories;

import com.practica.hoteles.reservas.dtos.HotelDto;
import com.practica.hoteles.reservas.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
