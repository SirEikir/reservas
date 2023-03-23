package com.practica.hoteles.reservas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityRange {
    private HotelDto hotel;
    private LocalDate initDate;
    private LocalDate endDate;
    private int rooms;
}






