package com.practica.hoteles.reservas.controllers;

import com.practica.hoteles.reservas.dtos.CreateHotelDto;
import com.practica.hoteles.reservas.dtos.HotelDto;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.exceptions.HotelNotFoundException;
import com.practica.hoteles.reservas.mappers.HotelMapper;
import com.practica.hoteles.reservas.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**

 Controlador para manejar peticiones relacionadas con hoteles.
 */
@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    @Autowired
    private final HotelService hotelService;

    /**

     Maneja la petición POST para crear un nuevo hotel.
     @param hotelDto DTO que contiene la información del hotel a crear.
     @return ResponseEntity con el DTO del hotel creado y código de estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@RequestBody CreateHotelDto hotelDto) {
        Hotel createdHotel = hotelService.createHotel(hotelDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(HotelMapper.hotelToDto(createdHotel));
    }
    /**

     Maneja la petición PUT para actualizar un hotel existente.
     @param id Identificador del hotel a actualizar.
     @param hotelDto DTO que contiene la información actualizada del hotel.
     @return ResponseEntity con el DTO del hotel actualizado y código de estado HTTP 200 (OK).
     @throws HotelNotFoundException si el hotel con el id especificado no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable long id,
                                                @RequestBody CreateHotelDto hotelDto) throws HotelNotFoundException {
        Hotel updatedHotel = hotelService.updateHotel(id, hotelDto);
        return ResponseEntity.ok(HotelMapper.hotelToDto((updatedHotel)));
    }
    /**

     Maneja la petición GET para obtener la información de un hotel específico.
     @param id Identificador del hotel a obtener.
     @return ResponseEntity con el objeto Hotel y código de estado HTTP 200 (OK).
     @throws HotelNotFoundException si el hotel con el id especificado no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable long id) throws HotelNotFoundException {
        Hotel hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotel);
    }
    /**

     Maneja la petición GET para obtener la información de todos los hoteles.
     @return ResponseEntity con una lista de DTOs de hoteles y código de estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels.stream().map(HotelMapper::hotelToDto).collect(Collectors.toList()));
    }
}
