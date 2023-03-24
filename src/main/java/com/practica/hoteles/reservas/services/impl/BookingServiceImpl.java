package com.practica.hoteles.reservas.services.impl;

import com.practica.hoteles.reservas.dtos.BookingDto;
import com.practica.hoteles.reservas.entities.Booking;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.exceptions.HotelNotFoundException;
import com.practica.hoteles.reservas.repositories.AvailabilityRepository;
import com.practica.hoteles.reservas.repositories.BookingRepository;
import com.practica.hoteles.reservas.repositories.HotelRepository;
import com.practica.hoteles.reservas.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService  {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public BookingDto createBooking(BookingDto bookingDto) {
        return null;
    }

    @Override
    public List<BookingDto> getBookingsByHotelAndDates(Long hotelId, LocalDate fromDate, LocalDate toDate) {
        return null;
    }

    @Override
    public BookingDto getBookingById(Long id) {
        return null;
    }

    @Override
    public void cancelBooking(Long id) {

    }
}
