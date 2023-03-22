package com.practica.hoteles.reservas.dtos;


import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BookingDto {
    private Long id;
    private Long hotelId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String email;

}
