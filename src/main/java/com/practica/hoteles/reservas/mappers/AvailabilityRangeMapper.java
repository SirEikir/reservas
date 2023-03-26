package com.practica.hoteles.reservas.mappers;

import com.practica.hoteles.reservas.dtos.AvailabilityRangeDto;
import com.practica.hoteles.reservas.entities.Hotel;

import java.time.LocalDate;

/**
 * Esta clase contiene métodos para mapear objetos AvailabilityRange a objetos AvailabilityRangeDto.
 */
public class AvailabilityRangeMapper {

    /**
     * Este método mapea un objeto AvailabilityRange a un objeto AvailabilityRangeDto.
     * @param hotel El objeto Hotel asociado con el rango de disponibilidad.
     * @param initDate La fecha de inicio del rango de disponibilidad.
     * @param endDate La fecha final del rango de disponibilidad.
     * @param rooms El número de habitaciones disponibles durante el rango de disponibilidad.
     * @return El objeto AvailabilityRangeDto correspondiente al objeto AvailabilityRange proporcionado.
     */
    public static AvailabilityRangeDto availabilityRangeToDto(Hotel hotel, LocalDate initDate, LocalDate endDate, int rooms) {
        return new AvailabilityRangeDto(HotelMapper.hotelToDto(hotel), initDate, endDate, rooms);
    }
}