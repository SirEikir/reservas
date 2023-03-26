package com.practica.hoteles.reservas.serviceTest;


import com.practica.hoteles.reservas.dtos.CreateHotelDto;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.exceptions.HotelNotFoundException;
import com.practica.hoteles.reservas.repositories.HotelRepository;
import com.practica.hoteles.reservas.services.impl.HotelServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    @Test(expected = IllegalArgumentException.class)
    public void createHotel_ThrowsExceptionWhenNameOrCategoryIsEmpty() {
        // Arrange
        CreateHotelDto dto = new CreateHotelDto();
        dto.setName("");
        dto.setCategory("");

        // Act
        hotelService.createHotel(dto);
    }

    @Test
    public void updateHotel_Success() {
        // Arrange
        Long id = 1L;
        CreateHotelDto dto = new CreateHotelDto("Test Hotel", "3 Estrellas");

        Hotel existingHotel = new Hotel("Hotel Test2", "3 Estrellas");
        Mockito.when(hotelRepository.findById(id)).thenReturn(Optional.of(existingHotel));

        Hotel updatedHotel = new Hotel(dto.getName(), dto.getCategory());
        updatedHotel.setId(existingHotel.getId());
        Mockito.when(hotelRepository.save(Mockito.any(Hotel.class))).thenReturn(updatedHotel);

        // Act
        Hotel result = hotelService.updateHotel(id, dto);

        // Assert
        Assert.assertEquals(updatedHotel.getId(), result.getId());
        Assert.assertEquals(updatedHotel.getName(), result.getName());
        Assert.assertEquals(updatedHotel.getCategory(), result.getCategory());
        Mockito.verify(hotelRepository, Mockito.times(1)).findById(id);
        Mockito.verify(hotelRepository, Mockito.times(1)).save(Mockito.any(Hotel.class));
    }

    @Test(expected = HotelNotFoundException.class)
    public void updateHotel_ThrowsExceptionWhenHotelNotFound() {
        // Arrange
        CreateHotelDto dto = new CreateHotelDto();
        dto.setName("Updated hotel name");
        dto.setCategory("Updated hotel category");
        Long nonExistingHotelId = 1234L;

        Mockito.when(hotelRepository.findById(nonExistingHotelId)).thenReturn(Optional.empty());

        // Act
        hotelService.updateHotel(nonExistingHotelId, dto);
    }


    @Test(expected = HotelNotFoundException.class)
    public void updateHotel_HotelNotFound() {
        // Arrange
        Long id = 1L;
        CreateHotelDto dto = new CreateHotelDto("Test Hotel", "3 Estrellas");

        Mockito.when(hotelRepository.findById(id)).thenReturn(Optional.empty());
        // Act
        hotelService.updateHotel(id, dto);

        // Assert
        // Expected exception: HotelNotFoundException
    }
    @Test
    public void getHotelById_Success() {
        // Arrange
        Long id = 1L;
        Hotel expectedHotel = new Hotel("Hotel ABC", "5-star");
        Mockito.when(hotelRepository.findById(id)).thenReturn(Optional.of(expectedHotel));

        // Act
        Hotel result = hotelService.getHotelById(id);

        // Assert
        assertEquals(expectedHotel.getId(), result.getId());
        assertEquals(expectedHotel.getName(), result.getName());
        assertEquals(expectedHotel.getCategory(), result.getCategory());
        verify(hotelRepository, Mockito.times(1)).findById(id);
    }

    @Test(expected = HotelNotFoundException.class)
    public void getHotelById_HotelNotFound() {
        // Arrange
        Long id = 1L;
        when(hotelRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        hotelService.getHotelById(id);

        // Assert
        // Expected exception: HotelNotFoundException
    }
    @Test(expected = HotelNotFoundException.class)
    public void getHotelById_ReturnsNullWhenHotelNotFound() {
        // Arrange
        Long nonExistingHotelId = 1234L;

        Mockito.when(hotelRepository.findById(nonExistingHotelId)).thenReturn(Optional.empty());

        // Act
        hotelService.getHotelById(nonExistingHotelId);

        // Assert
    }

    @Test
    public void getAllHotels_Success() {
        // Arrange
        List<Hotel> expectedHotels = Arrays.asList(
                new Hotel("Hotel ABC", "5-star"),
                new Hotel("Hotel XYZ", "4-star")
        );
        Mockito.when(hotelRepository.findAll()).thenReturn(expectedHotels);

        // Act
        List<Hotel> result = hotelService.getAllHotels();

        // Assert
        assertEquals(expectedHotels.size(), result.size());
        for (int i = 0; i < expectedHotels.size(); i++) {
            Assert.assertEquals(expectedHotels.get(i).getId(), result.get(i).getId());
            Assert.assertEquals(expectedHotels.get(i).getName(), result.get(i).getName());
            Assert.assertEquals(expectedHotels.get(i).getCategory(), result.get(i).getCategory());
        }
        Mockito.verify(hotelRepository, Mockito.times(1)).findAll();
    }
    @Test
    public void getAllHotels_ReturnsEmptyListWhenNoHotelsRegistered() {
        // Arrange
        Mockito.when(hotelRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Hotel> result = hotelService.getAllHotels();

        // Assert
        Assert.assertTrue(result.isEmpty());
    }

}
