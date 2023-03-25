package com.practica.hoteles.reservas.dtos;


import com.practica.hoteles.reservas.entities.Booking;
import com.practica.hoteles.reservas.entities.Hotel;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDto {
    private long id;
    private HotelDto hotel;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String email;

    public static BookingDto bookingToDto(Booking booking){
        BookingDto dtoResponse = new BookingDto(booking.getId(), HotelDto.hotelToDto(booking.getHotel()), booking.getDateFrom(), booking.getDateTo(), booking.getEmail());
        return dtoResponse;
    }

}
