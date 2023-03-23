package com.practica.hoteles.reservas.services.impl;

import com.practica.hoteles.reservas.dtos.AvailabilityRange;
import com.practica.hoteles.reservas.dtos.HotelDto;
import com.practica.hoteles.reservas.entities.Availability;
import com.practica.hoteles.reservas.entities.Booking;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.exceptions.AvailabilityNotFoundException;
import com.practica.hoteles.reservas.exceptions.HotelNotFoundException;
import com.practica.hoteles.reservas.repositories.AvailabilityRepository;
import com.practica.hoteles.reservas.repositories.BookingRepository;
import com.practica.hoteles.reservas.repositories.HotelRepository;
import com.practica.hoteles.reservas.services.AvailabilityService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

        @Autowired
        private AvailabilityRepository availabilityRepository;

        @Autowired
        private HotelRepository hotelRepository;

        @Autowired
        private BookingRepository bookingRepository;

        public Availability createAvailability(Hotel hotel, LocalDate date, int rooms) {
            // Encuentra el hotel por ID

            // Create a new availability entity
            Availability availability = new Availability();
            availability.setHotel(hotel);
            availability.setDate(date);
            availability.setRooms(rooms);

            // Save the availability entity
            return availabilityRepository.save(availability);
        }

        @Override
        public AvailabilityRange createAvailabilities (long hotelId, LocalDate initDate, LocalDate endDate, int rooms) {
            Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new AvailabilityNotFoundException(hotelId));
            List<Availability> availabilities = new LinkedList<>();
            LocalDate currentDate = initDate;

            while (!currentDate.isAfter(endDate)) {
                Availability availability = availabilityRepository.findByHotelIdAndDate(hotelId, currentDate);
                if (availability == null) {
                    // If availability does not exist for the current date, create a new one with the given number of rooms
                    availability = createAvailability(hotel, currentDate, rooms);
                } else {
                    // If availability already exists for the current date, add the given number of rooms to it
                    availability.setRooms(availability.getRooms() + rooms);
                }
                // Save the availability
                availabilityRepository.save(availability);
                // Add the availability to the list
                availabilities.add(availability);
                // Move to the next date
                currentDate = currentDate.plusDays(1);
            }
            return new AvailabilityRange(HotelDto.hotelToDto(hotel), initDate, endDate, rooms);

        }
        @Override
        public Availability getAvailabilityByHotelAndDate(long hotelId, LocalDate date) {
            throw new NotYetImplementedException();

//            HotelDto hotelDto = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel not found"));
//            Availability availability = availabilityRepository.findByHotelAndDate(hotel, date).orElseThrow(() -> new RuntimeException("Availability not found"));
//            return mapAvailabilityToAvailabilityDto(availability);
        }

        @Override
        public boolean checkAvailability(long hotelId, LocalDate fromDate, LocalDate toDate, int rooms) {
            throw new NotYetImplementedException();

//            Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException(hotelId));
//            List<Booking> bookings = bookingRepository.findByHotelIdAndDateFromLessThanEqualAndDateToGreaterThanEqual(hotelId, fromDate, toDate);
//            int totalRooms = bookings.stream().mapToInt(Booking::getRooms).sum();
//            Availability availability = (Availability) availabilityRepository.findByHotelIdAndDateBetween(hotelId, fromDate, toDate);
//            return availability.getRooms() - totalRooms >= rooms;
        }

        @Override
        public void updateAvailabilityByBooking(long bookingId) {
            throw new NotYetImplementedException();

//            Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
//            LocalDate fromDate = booking.getStartDate();
//            LocalDate toDate = booking.getEndDate();
//            Hotel hotel = booking.getHotel();
//            List<Booking> bookings = bookingRepository.findBookingsByHotelAndDateRange(hotel, fromDate, toDate);
//            int totalRooms = bookings.stream().mapToInt(Booking::getRooms).sum();
//            Availability availability = availabilityRepository.findByHotelAndDate(hotel, fromDate).orElseThrow(() -> new RuntimeException("Availability not found"));
//            availability.setRooms(availability.getRooms() + booking.getRooms() - totalRooms);
//            availabilityRepository.save(availability);
        }

        private Availability mapAvailabilityToAvailabilityDto(Availability availabilityDto) {
            throw new NotYetImplementedException();

//            AvailabilityDto availabilityDto = new AvailabilityDto();
//            availabilityDto.setId(availabilityDto.getId());
//            availabilityDto.setHotelId(availabilityDto.getHotel().getId());
//            availabilityDto.setDate(availabilityDto.getDate());
//            availabilityDto.setRooms(availabilityDto.getRooms());
//            return availabilityDto;
        }
    }

