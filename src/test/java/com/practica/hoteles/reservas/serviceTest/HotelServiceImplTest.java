package com.practica.hoteles.reservas.serviceTest;


import com.practica.hoteles.reservas.dtos.CreateHotelDto;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.repositories.HotelRepository;
import com.practica.hoteles.reservas.services.impl.HotelServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    public void testCreateHotel_Success() {
        // Given
        CreateHotelDto dto = new CreateHotelDto("Test Hotel", "3 Estrellas");
        Hotel hotel = new Hotel(dto.getName(), dto.getCategory());
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        // When
        Hotel result = hotelService.createHotel(dto);

        // Then
        verify(hotelRepository, times(1)).save(any(Hotel.class));
        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getCategory(), result.getCategory());
    }
}
