package com.practica.hoteles.reservas.services.impl;

import com.practica.hoteles.reservas.dtos.AvailabilityDto;
import com.practica.hoteles.reservas.dtos.HotelDto;
import com.practica.hoteles.reservas.entities.Availability;
import com.practica.hoteles.reservas.exceptions.AvailabilityNotFoundException;
import com.practica.hoteles.reservas.repositories.AvailabilityRepository;
import com.practica.hoteles.reservas.repositories.BookingRepository;
import com.practica.hoteles.reservas.repositories.HotelRepository;
import com.practica.hoteles.reservas.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

        @Autowired
        private AvailabilityRepository availabilityRepository;

        @Autowired
        private HotelRepository hotelRepository;

        @Autowired
        private BookingRepository bookingRepository;

        @Override
        public AvailabilityDto createAvailability(Long hotelId, LocalDate date, int rooms) {
            HotelDto hotelDto = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel not found"));
            AvailabilityDto availabilityDto = new AvailabilityDto();
            availabilityDto.setHotelId(hotelId);
            availabilityDto.setDate(date);
            availabilityDto.setRooms(rooms);
            AvailabilityDto savedAvailability = availabilityRepository.save(availabilityDto);
            return mapAvailabilityToAvailabilityDto(savedAvailability);
        }

        @Override
        public AvailabilityDto updateAvailability(Long availabilityId, int rooms) {
            Availability availability = availabilityRepository.findById(availabilityId).orElseThrow(() -> new RuntimeException("Availability not found"));
            availability.setRooms(rooms);
            Availability updatedAvailability = availabilityRepository.save(availability);
            return mapAvailabilityToAvailabilityDto(updatedAvailability);
        }

        @Override
        public AvailabilityDto getAvailability(Long availabilityId) {
            Availability availability = availabilityRepository.findById(availabilityId).orElseThrow(() -> new RuntimeException("Availability not found"));
            return mapAvailabilityToAvailabilityDto(availability);
        }

        @Override
        public AvailabilityDto getAvailabilityByHotelAndDate(Long hotelId, LocalDate date) {
            HotelDto hotelDto = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel not found"));
            Availability availability = availabilityRepository.findByHotelAndDate(hotel, date).orElseThrow(() -> new RuntimeException("Availability not found"));
            return mapAvailabilityToAvailabilityDto(availability);
        }

        @Override
        public boolean checkAvailability(Long hotelId, LocalDate fromDate, LocalDate toDate, int rooms) {
            Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel not found"));
            List<Booking> bookings = bookingRepository.findBookingsByHotelAndDateRange(hotel, fromDate, toDate);
            int totalRooms = bookings.stream().mapToInt(Booking::getRooms).sum();
            Availability availability = availabilityRepository.findByHotelAndDate(hotel, fromDate).orElseThrow(() -> new RuntimeException("Availability not found"));
            return availability.getRooms() - totalRooms >= rooms;
        }

        @Override
        public void updateAvailabilityByBooking(Long bookingId) {
            Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
            LocalDate fromDate = booking.getStartDate();
            LocalDate toDate = booking.getEndDate();
            Hotel hotel = booking.getHotel();
            List<Booking> bookings = bookingRepository.findBookingsByHotelAndDateRange(hotel, fromDate, toDate);
            int totalRooms = bookings.stream().mapToInt(Booking::getRooms).sum();
            Availability availability = availabilityRepository.findByHotelAndDate(hotel, fromDate).orElseThrow(() -> new RuntimeException("Availability not found"));
            availability.setRooms(availability.getRooms() + booking.getRooms() - totalRooms);
            availabilityRepository.save(availability);
        }

        private AvailabilityDto mapAvailabilityToAvailabilityDto(AvailabilityDto availabilityDto) {
            AvailabilityDto availabilityDto = new AvailabilityDto();
            availabilityDto.setId(availabilityDto.getId());
            availabilityDto.setHotelId(availabilityDto.getHotel().getId());
            availabilityDto.setDate(availabilityDto.getDate());
            availabilityDto.setRooms(availabilityDto.getRooms());
            return availabilityDto;
        }
    }
}
