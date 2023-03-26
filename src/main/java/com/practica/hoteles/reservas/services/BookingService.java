package com.practica.hoteles.reservas.services;

import com.practica.hoteles.reservas.dtos.BookingDto;
import com.practica.hoteles.reservas.entities.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    Booking createBookings(long hotelId, LocalDate fromDate, LocalDate toDate, String email);

    List<Booking> getBookingsByHotelIdAndDate(long hotelId,LocalDate fromDate, LocalDate toDate);

    Booking getBookingById(Long id);

    void cancelBooking(Long id);
}
