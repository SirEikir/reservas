package com.practica.hoteles.reservas.controllers;

import com.practica.hoteles.reservas.dtos.AvailabilityRange;
import com.practica.hoteles.reservas.dtos.HotelDto;
import com.practica.hoteles.reservas.entities.Availability;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
        public AvailabilityRange getAvailabilityByHotelAndDate(@RequestParam long hotelId,
                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
            Availability av =  availabilityService.getAvailabilityByHotelAndDate(hotelId, date);
            HotelDto hdto = HotelDto.hotelToDto(av.getHotel());
            return new AvailabilityRange(hdto, av.getDate(), av.getDate(), av.getRooms());
        }


        @GetMapping("/check")
        public List<HotelDto> checkAvailability(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initDate,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                @RequestParam @Nullable String hotelName,
                                                @RequestParam @Nullable String hotelCategory) {
            List<Hotel> hotels = availabilityService.checkAvailability(initDate, endDate,hotelName, hotelCategory);
            return hotels.stream().map(HotelDto::hotelToDto).collect(Collectors.toList());
        }

}
