package com.practica.hoteles.reservas.repositories;

import com.practica.hoteles.reservas.entities.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByHotelIdAndDateBetween(Long hotelId, LocalDate startDate, LocalDate endDate);
}