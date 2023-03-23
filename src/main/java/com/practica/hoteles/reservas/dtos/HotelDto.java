package com.practica.hoteles.reservas.dtos;

import com.practica.hoteles.reservas.entities.Hotel;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelDto {
    private Long id;
    private String name;
    private String category;

    public static HotelDto hotelToDto(Hotel hotel){
        HotelDto dtoResponse = new HotelDto(hotel.getId(), hotel.getName(), hotel.getCategory());
        return dtoResponse;
    }
}
