package com.practica.hoteles.reservas.services;

import com.practica.hoteles.reservas.dtos.BookingDto;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    BookingDto createBooking(BookingDto bookingDto);


    List<BookingDto> getBookingsByHotelAndDates(Long hotelId, LocalDate fromDate, LocalDate toDate);

    BookingDto getBookingById(Long id);

    void cancelBooking(Long id);
}
