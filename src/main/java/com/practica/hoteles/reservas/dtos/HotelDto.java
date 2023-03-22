package com.practica.hoteles.reservas.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class HotelDto {
    private Long id;
    private String name;
    private String category;
}
