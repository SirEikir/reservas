package com.practica.hoteles.reservas.services;

import com.practica.hoteles.reservas.dtos.CreateHotelDto;
import com.practica.hoteles.reservas.entities.Hotel;

import java.util.List;

public interface HotelService {
    Hotel createHotel(CreateHotelDto hotelDto);

    Hotel updateHotel(Long id, CreateHotelDto hotelDto);

    Hotel getHotelById(Long id);

    List<Hotel> getAllHotels();

//    AvailabilityDto openAvailability(Long hotelId, LocalDate fromDate, LocalDate toDate, int rooms);

//    List<HotelDto> getAvailableHotels(LocalDate fromDate, LocalDate toDate, String hotelName, String hotelCategory);

//    BookingDto createBooking(Long hotelId, LocalDate fromDate, LocalDate toDate, String email);

//    List<BookingDto> getBookingsByHotelAndDates(Long hotelId, LocalDate fromDate, LocalDate toDate);

//    BookingDto getBookingById(Long id);

//    void cancelBooking(Long id);
}
