package com.practica.hoteles.reservas.services;

import com.practica.hoteles.reservas.dtos.AvailabilityDto;

import java.time.LocalDate;

public interface AvailabilityService {
    AvailabilityDto createAvailability(Long hotelId, LocalDate date, int rooms);

    AvailabilityDto updateAvailability(Long availabilityId, int rooms);

    AvailabilityDto getAvailability(Long availabilityId);

    AvailabilityDto getAvailabilityByHotelAndDate(Long hotelId, LocalDate date);

    boolean checkAvailability(Long hotelId, LocalDate fromDate, LocalDate toDate, int rooms);

    void updateAvailabilityByBooking(Long bookingId);
}
