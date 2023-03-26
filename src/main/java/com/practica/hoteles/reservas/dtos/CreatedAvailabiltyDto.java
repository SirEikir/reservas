package com.practica.hoteles.reservas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
/**

 Clase que representa un objeto CreatedAvailabilityDto, utilizado para transferir información de la creación de una

 disponibilidad de habitaciones entre diferentes componentes del software.

 Esta clase contiene información sobre el hotel al que se refiere la disponibilidad de habitaciones, la fecha de

 inicio y fin de la disponibilidad, y el número de habitaciones disponibles para reserva.


 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatedAvailabiltyDto {
    /**

     Identificador único del hotel al que se refiere la disponibilidad de habitaciones.
     */
    private long hotelId;
    /**

     Fecha de inicio de la disponibilidad de habitaciones.
     */
    private LocalDate initDate;
    /**

     Fecha de fin de la disponibilidad de habitaciones.
     */
    private LocalDate endDate;
    /**

     Número de habitaciones disponibles para reserva en la disponibilidad de habitaciones.
     */
    private int rooms;
}
