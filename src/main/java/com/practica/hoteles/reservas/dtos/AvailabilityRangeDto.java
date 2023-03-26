package com.practica.hoteles.reservas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**

 Clase que representa un objeto AvailabilityRangeDto, utilizado para transferir información de la disponibilidad de

 habitaciones en un rango de fechas específico entre diferentes componentes del software.

 Esta clase contiene información sobre el objeto HotelDto al que se refiere la disponibilidad de habitaciones, la fecha

 de inicio y fin del rango de disponibilidad, y el número de habitaciones disponibles para reserva en el rango de

 fechas especificado.

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityRangeDto {

    /**
     * Objeto HotelDto al que se refiere la disponibilidad de habitaciones.
     */
    private HotelDto hotel;
    /**
     * Fecha de inicio del rango de disponibilidad de habitaciones.
     */
    private LocalDate initDate;
    /**
     * Fecha de fin del rango de disponibilidad de habitaciones.
     */
    private LocalDate endDate;
    /**
     * Número de habitaciones disponibles para reserva en el rango de fechas especificado.
     */
    private int rooms;
}







