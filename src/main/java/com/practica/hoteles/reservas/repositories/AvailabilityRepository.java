package com.practica.hoteles.reservas.repositories;

import com.practica.hoteles.reservas.entities.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByHotelIdAndDateBetween(Long hotelId, LocalDate initDate, LocalDate endDate);

    Availability findByHotelIdAndDate(long hotelId, LocalDate currentDate);


    //Crear filtros por nombre y categoria del hotel
}