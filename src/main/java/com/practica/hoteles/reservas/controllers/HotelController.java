package com.practica.hoteles.reservas.controllers;

import com.practica.hoteles.reservas.dtos.CreateHotelDto;
import com.practica.hoteles.reservas.dtos.HotelDto;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.practica.hoteles.reservas.dtos.HotelDto.hotelToDto;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    @Autowired
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@RequestBody CreateHotelDto hotelDto) {
        Hotel createdHotel = hotelService.createHotel(hotelDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelToDto(createdHotel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable Long id, @RequestBody CreateHotelDto hotelDto) {
        Hotel updatedHotel = hotelService.updateHotel(id, hotelDto);
        return ResponseEntity.ok(hotelToDto((updatedHotel)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotel);
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }


}
