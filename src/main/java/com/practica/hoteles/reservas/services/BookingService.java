package com.practica.hoteles.reservas.services;

import com.practica.hoteles.reservas.dtos.BookingDto;
import com.practica.hoteles.reservas.entities.Booking;
import com.practica.hoteles.reservas.exceptions.BookingNotFoundException;
import com.practica.hoteles.reservas.exceptions.HotelNotFoundException;
import com.practica.hoteles.reservas.exceptions.NotAvailableException;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    Booking createBookings(long hotelId, LocalDate fromDate, LocalDate toDate, String email) throws NotAvailableException, HotelNotFoundException;

    List<Booking> getBookingsByHotelIdAndDate(long hotelId,LocalDate fromDate, LocalDate toDate);

    Booking getBookingById(Long id) throws BookingNotFoundException;

    void cancelBooking(Long id) throws BookingNotFoundException;
}
