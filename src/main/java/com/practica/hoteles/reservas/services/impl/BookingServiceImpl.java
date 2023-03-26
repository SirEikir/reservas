package com.practica.hoteles.reservas.services.impl;

import com.practica.hoteles.reservas.entities.Availability;
import com.practica.hoteles.reservas.entities.Booking;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.exceptions.BookingNotFoundException;
import com.practica.hoteles.reservas.exceptions.HotelNotFoundException;
import com.practica.hoteles.reservas.exceptions.NotAvailableException;
import com.practica.hoteles.reservas.repositories.AvailabilityRepository;
import com.practica.hoteles.reservas.repositories.BookingRepository;
import com.practica.hoteles.reservas.repositories.HotelRepository;
import com.practica.hoteles.reservas.services.AvailabilityService;
import com.practica.hoteles.reservas.services.BookingService;
import com.practica.hoteles.reservas.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelService hotelService;


    public Booking createBooking(Hotel hotel, LocalDate initDate, LocalDate endDate, String email) {

        // Crear un rango de Reserva
        Booking booking = new Booking(hotel, initDate, endDate, email);

        // Guardar la entidad de la reserva
        return bookingRepository.save(booking);
    }

    @Override
    public Booking createBookings(long hotelId, LocalDate fromDate, LocalDate toDate, String email) throws HotelNotFoundException, NotAvailableException {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException(hotelId));
        List<Availability> availabilities = new LinkedList<>();
        LocalDate currentDate = fromDate;

        while (!currentDate.isAfter(toDate)) {
            Availability availability = availabilityRepository.findByHotelIdAndDate(hotelId, currentDate);
            if (availability == null || availability.getRooms() < 1) {
                // Si la disponibilidad no existe o no hay habitaciones disponibles, no podemos crear la reserva
                throw new NotAvailableException(hotelId);
            }
            availabilities.add(availability);
            currentDate = currentDate.plusDays(1);
        }

        Booking booking = createBooking(hotel, fromDate, toDate, email);
        // Restar las habitaciones utilizadas en cada día
        for (Availability availability : availabilities) {
            availability.setRooms(availability.getRooms() - 1);
            availabilityRepository.save(availability);
        }
        return booking;
    }

    @Override
    public List<Booking> getBookingsByHotelIdAndDate(long hotelId, LocalDate initDate, LocalDate endDate) {
        // Encontrar todas las reservas para el hotel y la fecha
        return bookingRepository.findByHotelIdAndDateToAfterAndDateFromBefore(hotelId, initDate, endDate);
    }
    @Override
    public Booking getBookingById(Long id) throws BookingNotFoundException {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
    }

    @Override
    public void cancelBooking(Long id) throws BookingNotFoundException {
        // Encontrar la reserva por ID
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));

        // Actualizar la disponibilidad para cada fecha de la reserva
        LocalDate currentDate = booking.getDateFrom();
        while (!currentDate.isAfter(booking.getDateTo())) {
            Availability availability = availabilityRepository.findByHotelIdAndDate(booking.getHotel().getId(), currentDate);
            availability.setRooms(availability.getRooms() + 1);
            availabilityRepository.save(availability);
            currentDate = currentDate.plusDays(1);
        }

        // Eliminar la reserva
        bookingRepository.delete(booking);
    }
}


