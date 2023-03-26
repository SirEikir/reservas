package com.practica.hoteles.reservas.dtos;

import lombok.*;

/**

 Clase que representa un objeto HotelDto, utilizado para transferir información de un hotel

 entre diferentes componentes del software.

 Esta clase contiene información básica sobre un hotel, como su identificador único, nombre

 y categoría.

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelDto {

    /**

     Identificador único del hotel.
     */
    private Long id;
    /**

     Nombre del hotel.
     */
    private String name;
    /**

     Categoría del hotel.
     */
    private String category;
}
