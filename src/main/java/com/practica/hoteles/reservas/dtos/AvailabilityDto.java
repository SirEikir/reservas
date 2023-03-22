package com.practica.hoteles.reservas.dtos;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AvailabilityDto {
    private Long id;
    private LocalDate date;
    private Long hotelId;
    private int rooms;
}






