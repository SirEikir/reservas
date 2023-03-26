package com.practica.hoteles.reservas.controllers;

import com.practica.hoteles.reservas.dtos.AvailabilityRangeDto;
import com.practica.hoteles.reservas.dtos.CreatedAvailabiltyDto;
import com.practica.hoteles.reservas.dtos.HotelDto;
import com.practica.hoteles.reservas.entities.Availability;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.exceptions.HotelNotFoundException;
import com.practica.hoteles.reservas.exceptions.NotAvailableException;
import com.practica.hoteles.reservas.mappers.HotelMapper;
import com.practica.hoteles.reservas.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**

 Controlador REST para las funcionalidades relacionadas con las disponibilidades de habitaciones en hoteles.
 */
@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    /**

     Constructor de la clase.
     @param availabilityService Servicio de disponibilidades.
     */
    @Autowired
    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }
    /**

     Crea una nueva disponibilidad en el sistema.
     @param createdAvailabiltyDto DTO con la información necesaria para crear la disponibilidad.
     @return DTO con la información de la disponibilidad creada.
     @throws HotelNotFoundException Si no se encuentra el hotel indicado.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvailabilityRangeDto createAvailability(@RequestBody CreatedAvailabiltyDto createdAvailabiltyDto) throws HotelNotFoundException {
        return availabilityService.createAvailabilities(createdAvailabiltyDto.getHotelId(),
                createdAvailabiltyDto.getInitDate(),
                createdAvailabiltyDto.getEndDate(),
                createdAvailabiltyDto.getRooms());
    }
    /**

     Obtiene la disponibilidad para un hotel en una fecha específica.
     @param hotelId Identificador del hotel.
     @param date Fecha para la cual se desea obtener la disponibilidad.
     @return DTO con la información de la disponibilidad para el hotel en la fecha especificada.
     @throws NotAvailableException Si no hay disponibilidad para el hotel en la fecha especificada.
     */
    @GetMapping
    public AvailabilityRangeDto getAvailabilityByHotelAndDate(@RequestParam long hotelId,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws NotAvailableException {
        Availability av = availabilityService.getAvailabilityByHotelAndDate(hotelId, date);
        HotelDto hdto = HotelMapper.hotelToDto(av.getHotel());
        return new AvailabilityRangeDto(hdto, av.getDate(), av.getDate(), av.getRooms());
    }
    /**

     Verifica la disponibilidad de habitaciones en hoteles para un rango de fechas específico.
     @param initDate Fecha de inicio del rango de fechas.
     @param endDate Fecha de fin del rango de fechas.
     @param hotelName Nombre del hotel (opcional).
     @param hotelCategory Categoría del hotel (opcional).
     @return Lista de DTOs con la información de los hoteles que tienen disponibilidad para el rango de fechas especificado.
     */
    @GetMapping("/check")
    public List<HotelDto> checkAvailability(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initDate,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                            @RequestParam @Nullable String hotelName,
                                            @RequestParam @Nullable String hotelCategory) {
        List<Hotel> hotels = availabilityService.checkAvailability(initDate, endDate,hotelName, hotelCategory);
        return hotels.stream().map(HotelMapper::hotelToDto).collect(Collectors.toList());
    }
}
