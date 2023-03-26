package com.practica.hoteles.reservas.mappers;

import com.practica.hoteles.reservas.dtos.HotelDto;
import com.practica.hoteles.reservas.entities.Hotel;

/**
 * Esta clase contiene métodos para mapear objetos Hotel a objetos HotelDto.
 */
public class HotelMapper {

    /**
     * Este método mapea un objeto Hotel a un objeto HotelDto.
     * @param hotel El objeto Hotel a ser mapeado.
     * @return El objeto HotelDto correspondiente al objeto Hotel proporcionado.
     */
    public static HotelDto hotelToDto(Hotel hotel) {
        HotelDto dtoResponse = new HotelDto(hotel.getId(), hotel.getName(), hotel.getCategory());
        return dtoResponse;
    }
}
