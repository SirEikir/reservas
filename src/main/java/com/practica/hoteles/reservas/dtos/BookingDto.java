package com.practica.hoteles.reservas.dtos;


import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDto {
    private Long id;
    private Long hotelId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String email;

}
