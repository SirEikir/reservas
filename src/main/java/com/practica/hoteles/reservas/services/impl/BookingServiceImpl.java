package com.practica.hoteles.reservas.services.impl;

import com.practica.hoteles.reservas.entities.Booking;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.exceptions.BookingNotFoundException;
import com.practica.hoteles.reservas.exceptions.HotelNotFoundException;
import com.practica.hoteles.reservas.repositories.AvailabilityRepository;
import com.practica.hoteles.reservas.repositories.BookingRepository;
import com.practica.hoteles.reservas.repositories.HotelRepository;
import com.practica.hoteles.reservas.services.BookingService;
import com.practica.hoteles.reservas.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelService hotelService;


    public Booking createBooking(Hotel hotel, LocalDate initDate, LocalDate endDate, String email) {

        // Create a new availability entity
        Booking booking = new Booking(hotel, initDate, endDate, email);

        // Save the availability entity
        return bookingRepository.save(booking);
    }

    @Override
    public Booking createBookings(long hotelId, LocalDate fromDate, LocalDate toDate, String email) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException(hotelId));
        return createBooking(hotel, fromDate, toDate, email);
    }

    @Override
    public List<Booking> getBookingsByHotelIdAndDate(long hotelId, LocalDate initDate, LocalDate endDate) {
        // Encontrar el hotel por ID
//        hotelService.getHotelById(hotelId);

        // Encontrar todas las reservas para el hotel y la fecha
        return bookingRepository.findByHotelIdAndDateToAfterAndDateFromBefore(hotelId, initDate, endDate);
    }
    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
    }

//
//    @Override
//    public void cancelBooking(Long id) {
//        // Encontrar la reserva por ID
//        Booking booking = bookingRepository.findById(id)
//                .orElseThrow(() -> new BookingNotFoundException(id));
//
//        // Actualizar la disponibilidad
//        Availability availability = availabilityRepository.findByHotelIdAndDate(booking.getHotel().getId(), booking.getDate());
//        availability.setRooms(availability.getRooms() + booking.getRooms());
//        availabilityRepository.save(availability);
//
//        // Eliminar la reserva
//        bookingRepository.delete(booking);
//    }
}


