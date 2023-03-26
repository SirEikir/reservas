package com.practica.hoteles.reservas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**

 Clase que representa un objeto CreateHotelDto, utilizado para crear un nuevo hotel en el sistema.

 Esta clase contiene información básica sobre el hotel que se desea crear, como su nombre y categoría.

 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHotelDto {

    /**

     Nombre del hotel a crear.
     */
    private String name;
    /**

     Categoría del hotel a crear.
     */
    private String category;
}
