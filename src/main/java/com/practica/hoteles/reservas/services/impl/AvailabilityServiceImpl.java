package com.practica.hoteles.reservas.services.impl;

import com.practica.hoteles.reservas.dtos.AvailabilityDto;
import com.practica.hoteles.reservas.entities.Availability;
import com.practica.hoteles.reservas.exceptions.AvailabilityNotFoundException;
import com.practica.hoteles.reservas.repositories.AvailabilityRepository;
import com.practica.hoteles.reservas.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    @Autowired
    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public AvailabilityDto createAvailability(Long hotelId, LocalDate date, int rooms) {
        Availability availability = new Availability(hotelId, date, rooms);
        return AvailabilityMapper.toAvailabilityDto(availabilityRepository.save(availability));
    }

    @Override
    public AvailabilityDto updateAvailability(Long availabilityId, int rooms) {
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new AvailabilityNotFoundException(availabilityId));
        availability.setRooms(rooms);
        return AvailabilityMapper.toAvailabilityDto(availabilityRepository.save(availability));
    }

    @Override
    public AvailabilityDto getAvailability(Long availabilityId) {
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new AvailabilityNotFoundException(availabilityId));
        return AvailabilityMapper.toAvailabilityDto(availability);
    }

    @Override
    public AvailabilityDto getAvailabilityByHotelAndDate(Long hotelId, LocalDate date) {
        Availability availability = availabilityRepository.findByHotelIdAndDate(hotelId, date)
                .orElseThrow(() -> new AvailabilityNotFoundException(hotelId, date));
        return AvailabilityMapper.toAvailabilityDto(availability);
    }

    @Override
    public boolean checkAvailability(Long hotelId, LocalDate fromDate, LocalDate toDate, int rooms) {
        return availabilityRepository.findByHotelIdAndDateBetween(hotelId, fromDate, toDate, rooms) > 0;
    }

    @Override
    public void updateAvailabilityByBooking(Long bookingId) {
        Availability availability = availabilityRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new AvailabilityNotFoundException(bookingId));
        availability.setRooms(availability.getRooms() - 1);
        availabilityRepository.save(availability);
    }
}
