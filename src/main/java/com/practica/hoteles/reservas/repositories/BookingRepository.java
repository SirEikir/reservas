package com.practica.hoteles.reservas.repositories;

import com.practica.hoteles.reservas.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByHotelIdAndDateToAfterAndDateFromBefore(Long hotelId, LocalDate startDate, LocalDate endDate);
}
