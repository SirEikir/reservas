package com.practica.hoteles.reservas.controllers;

import com.practica.hoteles.reservas.dtos.AvailabilityRange;
import com.practica.hoteles.reservas.entities.Availability;
import com.practica.hoteles.reservas.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {

        private final AvailabilityService availabilityService;

        @Autowired
        public AvailabilityController(AvailabilityService availabilityService) {
            this.availabilityService = availabilityService;
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public AvailabilityRange createAvailability(@RequestParam long hotelId,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initDate,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                    @RequestParam int rooms) {
            return availabilityService.createAvailabilities(hotelId, initDate, endDate, rooms);
        }

        @GetMapping
        public Availability getAvailabilityByHotelAndDate(@RequestParam Long hotelId,
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
