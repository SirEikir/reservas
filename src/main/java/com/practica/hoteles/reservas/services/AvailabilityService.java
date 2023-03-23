package com.practica.hoteles.reservas.services;

import com.practica.hoteles.reservas.dtos.AvailabilityRange;
import com.practica.hoteles.reservas.entities.Availability;

import java.time.LocalDate;

public interface AvailabilityService {
    AvailabilityRange createAvailabilities(long hotelId, LocalDate initDate, LocalDate endDate, int rooms);

    Availability getAvailabilityByHotelAndDate(long hotelId, LocalDate date);

    boolean checkAvailability(long hotelId, LocalDate fromDate, LocalDate toDate, int rooms);

    void updateAvailabilityByBooking(long bookingId);
}
