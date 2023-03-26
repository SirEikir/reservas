package com.practica.hoteles.reservas.dtos;


import com.practica.hoteles.reservas.entities.Booking;
import com.practica.hoteles.reservas.entities.Hotel;
import lombok.*;

import java.time.LocalDate;

/**

 Clase que representa un objeto BookingDto, utilizado para transferir información de una reserva entre diferentes

 componentes del software.

 Esta clase contiene información sobre la reserva, como su identificador único, el objeto HotelDto al que se refiere

 la reserva, las fechas de inicio y fin de la reserva y el correo electrónico del huésped que realizó la reserva.

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDto {

    /**

     Identificador único de la reserva.
     */
    private long id;
    /**

     Objeto HotelDto al que se refiere la reserva.
     */
    private HotelDto hotel;
    /**

     Fecha de inicio de la reserva.
     */
    private LocalDate dateFrom;
    /**

     Fecha de fin de la reserva.
     */
    private LocalDate dateTo;
    /**

     Correo electrónico del huésped que realizó la reserva.
     */
    private String email;
}
