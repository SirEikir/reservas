package com.practica.hoteles.reservas.controllers;

import com.practica.hoteles.reservas.dtos.AvailabilityRange;
import com.practica.hoteles.reservas.dtos.BookingDto;
import com.practica.hoteles.reservas.dtos.HotelDto;
import com.practica.hoteles.reservas.entities.Booking;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.services.BookingService;
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
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto createBooking(@RequestParam long hotelId,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initDate,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                    @RequestParam String email) {
        Booking bookings = bookingService.createBookings(hotelId, initDate, endDate, email);
        return BookingDto.bookingToDto((bookings));
    }
    @GetMapping("/check")
    public ResponseEntity<List<BookingDto>> getBookings(@RequestParam long hotelId,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initDate,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Booking> bookings = bookingService.getBookingsByHotelIdAndDate(hotelId, initDate, endDate);
        return new ResponseEntity<>(bookings.stream().map(BookingDto::bookingToDto).collect(Collectors.toList()), HttpStatus.OK);
    }
    @GetMapping("/book")
    public ResponseEntity<BookingDto> getBookingsByHotelAndDates(@RequestParam long id){
        BookingDto booking = BookingDto.bookingToDto(bookingService.getBookingById(id));
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
//        bookingService.cancelBooking(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}
