package com.practica.hoteles.reservas.services;

import com.practica.hoteles.reservas.dtos.AvailabilityRange;
import com.practica.hoteles.reservas.dtos.HotelDto;
import com.practica.hoteles.reservas.entities.Availability;
import com.practica.hoteles.reservas.entities.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {
    AvailabilityRange createAvailabilities(long hotelId, LocalDate initDate, LocalDate endDate, int rooms);
    List<Hotel> checkAvailability(LocalDate fromDate, LocalDate toDate, String hotelName, String hotelCategory);
    Availability getAvailabilityByHotelAndDate(long hotelId, LocalDate date);
}
