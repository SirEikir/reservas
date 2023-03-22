package com.practica.hoteles.reservas.controllers;

import com.practica.hoteles.reservas.dtos.BookingDto;
import com.practica.hoteles.reservas.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingDto bookingDto) {
        BookingDto newBooking = bookingService.createBooking(bookingDto);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBooking(@PathVariable Long id) {
        BookingDto reservationDTO = bookingService.getBookingById(id);
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<BookingDto>> getBookingsByHotelAndDates(
            @PathVariable Long hotelId,
            @RequestParam LocalDate dateFrom,
            @RequestParam LocalDate dateTo) {
        List<BookingDto> reservations = bookingService.getBookingsByHotelAndDates(hotelId, dateFrom, dateTo);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
