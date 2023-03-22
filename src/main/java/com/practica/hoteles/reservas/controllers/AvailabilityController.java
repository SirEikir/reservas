package com.practica.hoteles.reservas.controllers;

import com.practica.hoteles.reservas.dtos.AvailabilityDto;
import com.practica.hoteles.reservas.services.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {

        private final AvailabilityService availabilityService;

        @Autowired
        public AvailabilityController(AvailabilityService availabilityService) {
            this.availabilityService = availabilityService;
        }

        @PostMapping("/")
        @ResponseStatus(HttpStatus.CREATED)
        public AvailabilityDto createAvailability(@RequestParam Long hotelId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam int rooms) {
            return availabilityService.createAvailability(hotelId, date, rooms);
        }

        @PutMapping("/{availabilityId}")
        public AvailabilityDto updateAvailability(@PathVariable Long availabilityId, @RequestParam int rooms) {
            return availabilityService.updateAvailability(availabilityId, rooms);
        }

        @GetMapping("/{availabilityId}")
        public AvailabilityDto getAvailability(@PathVariable Long availabilityId) {
            return availabilityService.getAvailability(availabilityId);
        }

        @GetMapping("/")
        public AvailabilityDto getAvailabilityByHotelAndDate(@RequestParam Long hotelId,
                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
            return availabilityService.getAvailabilityByHotelAndDate(hotelId, date);
        }

        @GetMapping("/check")
        public boolean checkAvailability(@RequestParam Long hotelId,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                         @RequestParam int rooms) {
            return availabilityService.checkAvailability(hotelId, fromDate, toDate, rooms);
        }

        @PostMapping("/booking/{bookingId}")
        public void updateAvailabilityByBooking(@PathVariable Long bookingId) {
            availabilityService.updateAvailabilityByBooking(bookingId);
        }
}
