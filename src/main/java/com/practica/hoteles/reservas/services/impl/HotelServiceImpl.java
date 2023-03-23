package com.practica.hoteles.reservas.services.impl;


import com.practica.hoteles.reservas.dtos.CreateHotelDto;
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
    public Hotel createHotel(CreateHotelDto dto) {
        Hotel hotel = new Hotel(dto.getName(), dto.getCategory());
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel updateHotel(Long id, CreateHotelDto hotelDto) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
        hotel.setName(hotelDto.getName());
        hotel.setCategory(hotelDto.getCategory());
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

}
