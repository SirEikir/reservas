package com.practica.hoteles.reservas.repositories;

import com.practica.hoteles.reservas.entities.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByHotelIdAndDateBetween(Long hotelId, LocalDate initDate, LocalDate endDate);
    Availability findByHotelIdAndDate(long hotelId, LocalDate date);
    List<Availability> findByDateBetween (LocalDate initDate, LocalDate endDate);
}