package com.practica.hoteles.reservas.services.impl;


import com.practica.hoteles.reservas.dtos.HotelDto;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.exceptions.HotelNotFoundException;
import com.practica.hoteles.reservas.repositories.HotelRepository;
import com.practica.hoteles.reservas.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public HotelDto createHotel(HotelDto hotelDto) {
        Hotel hotel = new Hotel(hotelDto.getName(), hotelDto.getCategory());
        Hotel savedHotel = hotelRepository.save(hotel);
        return new HotelDto(savedHotel.getId(), savedHotel.getName(), savedHotel.getCategory());
    }

    @Override
    public HotelDto updateHotel(Long id, HotelDto hotelDto) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
        hotel.setName(hotelDto.getName());
        hotel.setCategory(hotelDto.getCategory());
        Hotel savedHotel = hotelRepository.save(hotel);
        return new HotelDto(savedHotel.getId(), savedHotel.getName(), savedHotel.getCategory());
    }

    @Override
    public HotelDto getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
        return new HotelDto(hotel.getId(), hotel.getName(), hotel.getCategory());
    }

    @Override
    public List<HotelDto> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream()
                .map(hotel -> new HotelDto(hotel.getId(), hotel.getName(), hotel.getCategory()))
                .collect(Collectors.toList());
    }

}
