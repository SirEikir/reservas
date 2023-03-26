package com.practica.hoteles.reservas.controllers;

import com.practica.hoteles.reservas.dtos.BookingDto;
import com.practica.hoteles.reservas.entities.Booking;
import com.practica.hoteles.reservas.exceptions.BookingNotFoundException;
import com.practica.hoteles.reservas.exceptions.HotelNotFoundException;
import com.practica.hoteles.reservas.exceptions.NotAvailableException;
import com.practica.hoteles.reservas.mappers.BookingMapper;
import com.practica.hoteles.reservas.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**

 Controlador para manejar las operaciones relacionadas con reservas de hoteles.
 */
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    /**

     Crea un nuevo controlador de reservas y lo vincula con un servicio de reservas.
     @param bookingService Servicio de reservas a utilizar.
     */
    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    /**

     Crea una nueva reserva de hotel.
     @param hotelId ID del hotel donde se desea hacer la reserva.
     @param initDate Fecha de inicio de la reserva.
     @param endDate Fecha de fin de la reserva.
     @param email Correo electrónico del huésped que hace la reserva.
     @return DTO de reserva creada.
     @throws NotAvailableException Si el hotel no tiene habitaciones disponibles para las fechas especificadas.
     @throws HotelNotFoundException Si el hotel especificado no existe.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto createBooking(@RequestParam long hotelId,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initDate,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                    @RequestParam String email) throws NotAvailableException, HotelNotFoundException {
        Booking bookings = bookingService.createBookings(hotelId, initDate, endDate, email);
        return BookingMapper.bookingToDto((bookings));
    }
    /**

     Obtiene todas las reservas realizadas en un hotel para un rango de fechas determinado.
     @param hotelId ID del hotel para el que se quieren obtener las reservas.
     @param initDate Fecha de inicio del rango de fechas.
     @param endDate Fecha de fin del rango de fechas.
     @return Lista de DTOs de reservas correspondientes al rango de fechas especificado.
     */
    @GetMapping("/check")
    public ResponseEntity<List<BookingDto>> getBookings(@RequestParam long hotelId,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initDate,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Booking> bookings = bookingService.getBookingsByHotelIdAndDate(hotelId, initDate, endDate);
        return new ResponseEntity<>(bookings.stream().map(BookingMapper::bookingToDto).collect(Collectors.toList()), HttpStatus.OK);
    }
    /**

     Obtiene una reserva por ID.
     @param id ID de la reserva a buscar.
     @return DTO de reserva correspondiente al ID especificado.
     @throws BookingNotFoundException Si no se encuentra ninguna reserva con el ID especificado.
     */
    @GetMapping("/book/{id}")
    public ResponseEntity<BookingDto> getBookingsByHotelAndDates(@RequestParam long id) throws BookingNotFoundException {
        BookingDto booking = BookingMapper.bookingToDto(bookingService.getBookingById(id));
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    /**
     Cancela una reserva por ID.
     @param id ID de la reserva a cancelar.
     @return Respuesta HTTP indicando si se canceló la reserva o no.
     */
    @DeleteMapping
    public ResponseEntity<?> cancelBooking(@RequestParam long id) {
        try {
            bookingService.cancelBooking(id);
            return ResponseEntity.ok().build();
        } catch (BookingNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
