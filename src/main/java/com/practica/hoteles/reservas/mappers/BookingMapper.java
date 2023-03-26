package com.practica.hoteles.reservas.mappers;

import com.practica.hoteles.reservas.dtos.BookingDto;
import com.practica.hoteles.reservas.entities.Booking;

/**
 * Esta clase contiene métodos para mapear objetos Booking a objetos BookingDto.
 */
public class BookingMapper {

    /**
     * Este método mapea un objeto Booking a un objeto BookingDto.
     * @param booking El objeto Booking a ser mapeado.
     * @return El objeto BookingDto correspondiente al objeto Booking proporcionado.
     */
    public static BookingDto bookingToDto(Booking booking) {
        return new BookingDto(booking.getId(), HotelMapper.hotelToDto(booking.getHotel()), booking.getDateFrom(), booking.getDateTo(), booking.getEmail());
    }
}
