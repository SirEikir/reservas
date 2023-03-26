package com.practica.hoteles.reservas.services;

import com.practica.hoteles.reservas.dtos.AvailabilityRangeDto;
import com.practica.hoteles.reservas.entities.Availability;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.exceptions.AvailabilityNotFoundException;
import com.practica.hoteles.reservas.exceptions.HotelNotFoundException;
import com.practica.hoteles.reservas.exceptions.NotAvailableException;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {
    AvailabilityRangeDto createAvailabilities(long hotelId, LocalDate initDate, LocalDate endDate, int rooms) throws HotelNotFoundException;
    List<Hotel> checkAvailability(LocalDate fromDate, LocalDate toDate, String hotelName, String hotelCategory);
    Availability getAvailabilityByHotelAndDate(long hotelId, LocalDate date) throws NotAvailableException;
}
