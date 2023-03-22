package com.practica.hoteles.reservas.services.impl;

import com.practica.hoteles.reservas.dtos.BookingDto;
import com.practica.hoteles.reservas.entities.Booking;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.exceptions.HotelNotFoundException;
import com.practica.hoteles.reservas.repositories.BookingRepository;
import com.practica.hoteles.reservas.repositories.HotelRepository;
import com.practica.hoteles.reservas.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookingDto createBooking(Long hotelId, LocalDate dateFrom, LocalDate dateTo, String email) throws HotelNotFoundException {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            if (checkAvailability(hotel, dateFrom, dateTo)) {
                Booking booking = new Booking();
                booking.setHotel(hotel);
                booking.setDateFrom(dateFrom);
                booking.setDateTo(dateTo);
                booking.setEmail(email);
                Booking savedBooking = bookingRepository.save(booking);
                updateAvailabilityByBooking(savedBooking.getId(), hotelId, dateFrom, dateTo);
                return modelMapper.map(savedBooking, BookingDto.class);
            } else {
                throw new IllegalArgumentException("No hay disponibilidad en las fechas seleccionadas");
            }
        } else {
            throw new HotelNotFoundException("Hotel no encontrado con id: " + hotelId);
        }
    }

    private boolean checkAvailability(Hotel hotel, LocalDate fromDate, LocalDate toDate) {
        List<Boolean> availabilityList = hotel.getAvailabilityList()
                .stream()
                .filter(availability -> !availability.getDate().isBefore(fromDate) && !availability.getDate().isAfter(toDate))
                .map(availability -> availability.getRooms() > 0)
                .collect(Collectors.toList());
        return true;

    }
}
