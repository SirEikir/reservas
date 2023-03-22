package com.practica.hoteles.reservas.services;

import com.practica.hoteles.reservas.dtos.AvailabilityDto;
import com.practica.hoteles.reservas.dtos.BookingDto;
import com.practica.hoteles.reservas.dtos.HotelDto;

import java.time.LocalDate;
import java.util.List;

public interface HotelService {
    HotelDto createHotel(HotelDto hotelDto);

    HotelDto updateHotel(Long id, HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    List<HotelDto> getAllHotels();

    AvailabilityDto openAvailability(Long hotelId, LocalDate fromDate, LocalDate toDate, int rooms);

    List<HotelDto> getAvailableHotels(LocalDate fromDate, LocalDate toDate, String hotelName, String hotelCategory);

    BookingDto createBooking(Long hotelId, LocalDate fromDate, LocalDate toDate, String email);

    List<BookingDto> getBookingsByHotelAndDates(Long hotelId, LocalDate fromDate, LocalDate toDate);

    BookingDto getBookingById(Long id);

    void cancelBooking(Long id);
}
