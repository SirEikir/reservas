package com.practica.hoteles.reservas.serviceTest;

import com.practica.hoteles.reservas.dtos.AvailabilityRangeDto;
import com.practica.hoteles.reservas.dtos.HotelDto;
import com.practica.hoteles.reservas.entities.Availability;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.exceptions.AvailabilityNotFoundException;
import com.practica.hoteles.reservas.exceptions.HotelNotFoundException;
import com.practica.hoteles.reservas.exceptions.NotAvailableException;
import com.practica.hoteles.reservas.mappers.HotelMapper;
import com.practica.hoteles.reservas.repositories.AvailabilityRepository;
import com.practica.hoteles.reservas.repositories.HotelRepository;
import com.practica.hoteles.reservas.services.impl.AvailabilityServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@RunWith(MockitoJUnitRunner.class)
public class AvailabilityServiceImplTest {

    @InjectMocks
    private AvailabilityServiceImpl availabilityService;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Test
    public void createAvailability_shouldCreateAvailability() {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        LocalDate date = LocalDate.of(2023, 4, 1);
        int rooms = 10;

        Availability availability = new Availability();
        availability.setHotel(hotel);
        availability.setDate(date);
        availability.setRooms(rooms);

        Mockito.when(availabilityRepository.save(Mockito.any(Availability.class))).thenReturn(availability);

        // Act
        Availability result = availabilityService.createAvailability(hotel, date, rooms);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(hotel, result.getHotel());
        Assert.assertEquals(date, result.getDate());
        Assert.assertEquals(rooms, result.getRooms());
    }

    @Test
    public void createAvailabilities_shouldCreateAvailabilities() throws AvailabilityNotFoundException, HotelNotFoundException {
        // Arrange
        long hotelId = 1L;
        LocalDate initDate = LocalDate.of(2023, 4, 1);
        LocalDate endDate = LocalDate.of(2023, 4, 5);
        int rooms = 10;

        Hotel hotel = new Hotel();
        hotel.setId(hotelId);

        Mockito.when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));

        Availability availability = new Availability();
        availability.setHotel(hotel);
        availability.setDate(initDate);
        availability.setRooms(rooms);

        Mockito.when(availabilityRepository.findByHotelIdAndDate(hotelId, initDate)).thenReturn(null);
        Mockito.when(availabilityRepository.save(Mockito.any(Availability.class))).thenReturn(availability);

        // Act
        AvailabilityRangeDto result = availabilityService.createAvailabilities(hotelId, initDate, endDate, rooms);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(HotelMapper.hotelToDto(hotel), result.getHotel());
        Assert.assertEquals(initDate, result.getInitDate());
        Assert.assertEquals(endDate, result.getEndDate());
        Assert.assertEquals(rooms, result.getRooms());
    }

    @Test
    public void createAvailabilities_shouldUpdateAvailabilities() throws AvailabilityNotFoundException, HotelNotFoundException {
        // Arrange
        long hotelId = 1L;
        LocalDate initDate = LocalDate.of(2023, 4, 1);
        LocalDate endDate = LocalDate.of(2023, 4, 5);
        int rooms = 10;

        Hotel hotel = new Hotel();
        hotel.setId(hotelId);

        Availability availability = new Availability();
        availability.setHotel(hotel);
        availability.setDate(initDate);
        availability.setRooms(rooms);

        Mockito.when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        Mockito.when(availabilityRepository.findByHotelIdAndDate(hotelId, initDate)).thenReturn(availability);

        // Act
        AvailabilityRangeDto result = availabilityService.createAvailabilities(hotelId, initDate, endDate, rooms);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(HotelMapper.hotelToDto(hotel), result.getHotel());
        Assert.assertEquals(initDate, result.getInitDate());
        Assert.assertEquals(endDate, result.getEndDate());
        Assert.assertEquals(rooms, result.getRooms());
    }
    @Test
    public void getAvailabilityByHotelAndDate_shouldReturnAvailability() throws NotAvailableException {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        LocalDate date = LocalDate.of(2023, 4, 1);

        Availability availability = new Availability();
        availability.setHotel(hotel);
        availability.setDate(date);
        availability.setRooms(10);

        Mockito.when(availabilityRepository.findByHotelIdAndDate(1L, date)).thenReturn(availability);

        // Act
        Availability result = availabilityService.getAvailabilityByHotelAndDate(1L, date);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(hotel, result.getHotel());
        Assert.assertEquals(date, result.getDate());
        Assert.assertEquals(10, result.getRooms());
    }
    @Test
    public void getAvailabilityByHotelAndDate_shouldReturnNullWhenAvailabilityNotFound() {
        // Arrange
        long hotelId = 1L;
        LocalDate date = LocalDate.of(2023, 3, 26);
        Mockito.when(availabilityRepository.findByHotelIdAndDate(hotelId, date)).thenReturn(null);

        // Act & Assert
        Assert.assertThrows(NotAvailableException.class, () -> availabilityService.getAvailabilityByHotelAndDate(hotelId, date));
    }
    @Test
    public void checkAvailability_shouldReturnAvailableHotels() {
        // Arrange
        LocalDate fromDate = LocalDate.of(2023, 3, 1);
        LocalDate toDate = LocalDate.of(2023, 3, 5);

        Hotel hotel1 = new Hotel();
        hotel1.setId(1L);
        hotel1.setName("Hotel Test1");
        hotel1.setCategory("3 Estrellas");

        Hotel hotel2 = new Hotel();
        hotel2.setId(2L);
        hotel2.setName("Hotel Test2");
        hotel2.setCategory("4 Estrellas");

        Hotel hotel3 = new Hotel();
        hotel3.setId(3L);
        hotel3.setName("Hotel Test3");
        hotel3.setCategory("5 Estrellas");

        List<Availability> availabilities = new ArrayList<>();
        availabilities.add(new Availability(fromDate, hotel1,  10));
        availabilities.add(new Availability(fromDate.plusDays(1),hotel1,  10));
        availabilities.add(new Availability(fromDate.plusDays(2),hotel1,  2));
        availabilities.add(new Availability(fromDate.plusDays(3),hotel1,  5));
        availabilities.add(new Availability(fromDate.plusDays(4),hotel1,  10));
        availabilities.add(new Availability(fromDate, hotel2,  5));
        availabilities.add(new Availability(fromDate.plusDays(1),hotel2, 10));
        availabilities.add(new Availability(fromDate.plusDays(2),hotel2, 10));
        availabilities.add(new Availability(fromDate.plusDays(3),hotel2, 10));
        availabilities.add(new Availability(fromDate.plusDays(4),hotel2, 10));
        availabilities.add(new Availability(fromDate, hotel3,  8));
        availabilities.add(new Availability(fromDate.plusDays(1),hotel3, 10));
        availabilities.add(new Availability(fromDate.plusDays(2),hotel3, 10));
        availabilities.add(new Availability(fromDate.plusDays(3),hotel3, 10));
        availabilities.add(new Availability(fromDate.plusDays(4),hotel3, 10));

        Mockito.when(availabilityRepository.findByDateBetween(fromDate, toDate)).thenReturn(availabilities);

        // Act
        List<Hotel> availableHotels = availabilityService.checkAvailability(fromDate, toDate, null, null);

        // Assert
        Assert.assertEquals(3, availableHotels.size());
        Assert.assertTrue(availableHotels.contains(hotel1));
        Assert.assertTrue(availableHotels.contains(hotel2));
        Assert.assertTrue(availableHotels.contains(hotel3));
    }
    @Test
    public void checkAvailability_shouldReturnAvailableHotelsWithoutFilters() {
        // Arrange
        LocalDate fromDate = LocalDate.of(2023, 4, 1);
        LocalDate toDate = LocalDate.of(2023, 4, 5);

        // Disponibilidad para el hotel 1
        Availability availability1 = new Availability();
        availability1.setHotel(new Hotel("hotel Test1", "3 Estrellas"));
        availability1.setDate(LocalDate.of(2023, 4, 1));
        availability1.setRooms(1);
        Availability availability2 = new Availability();
        availability2.setHotel(new Hotel("hotel Test1", "3 Estrellas"));
        availability2.setDate(LocalDate.of(2023, 4, 2));
        availability2.setRooms(3);
        Availability availability3 = new Availability();
        availability3.setHotel(new Hotel("hotel Test1", "3 Estrellas"));
        availability3.setDate(LocalDate.of(2023, 4, 3));
        availability3.setRooms(2);
        Availability availability4 = new Availability();
        availability4.setHotel(new Hotel("hotel Test1", "3 Estrellas"));
        availability4.setDate(LocalDate.of(2023, 4, 4));
        availability4.setRooms(1);
        Availability availability5 = new Availability();
        availability5.setHotel(new Hotel("hotel Test1", "3 Estrellas"));
        availability5.setDate(LocalDate.of(2023, 4, 5));
        availability5.setRooms(4);

        // Disponibilidad para el hotel 2
        Availability availability6 = new Availability();
        availability6.setHotel(new Hotel("hotel Test2", "5 Estrellas"));
        availability6.setDate(LocalDate.of(2023, 4, 1));
        availability6.setRooms(1);
        Availability availability7 = new Availability();
        availability7.setHotel(new Hotel("hotel Test2", "5 Estrellas"));
        availability7.setDate(LocalDate.of(2023, 4, 2));
        availability7.setRooms(1);
        Availability availability8 = new Availability();
        availability8.setHotel(new Hotel("hotel Test2", "5 Estrellas"));
        availability8.setDate(LocalDate.of(2023, 4, 3));
        availability8.setRooms(1);
        Availability availability9 = new Availability();
        availability9.setHotel(new Hotel("hotel Test2", "5 Estrellas"));
        availability9.setDate(LocalDate.of(2023, 4, 4));
        availability9.setRooms(1);
        Availability availability10 = new Availability();
        availability10.setHotel(new Hotel("hotel Test2", "5 Estrellas"));
        availability10.setDate(LocalDate.of(2023, 4, 5));
        availability10.setRooms(1);

        List<Availability> availabilities = Arrays.asList(availability1, availability2, availability3, availability4,
                availability5, availability6, availability7, availability8, availability9, availability10);

        Mockito.when(availabilityRepository.findByDateBetween(fromDate, toDate)).thenReturn(availabilities);

        // Act
        List<Hotel> availableHotels = availabilityService.checkAvailability(fromDate, toDate, null, null);

        // Assert
        Assert.assertEquals(2, availableHotels.size());
        Assert.assertTrue(availableHotels.contains(new Hotel("hotel Test1","3 Estrellas")));
        Assert.assertTrue(availableHotels.contains(new Hotel("hotel Test2","5 Estrellas")));
    }

    @Test
    public void checkAvailability_shouldReturnAvailableHotelsWithFilters() {
        // Arrange
        Hotel hotel1 = new Hotel("Hotel Test1", "3 Estrellas");
        Hotel hotel2 = new Hotel("Hotel Test2", "5 Estrellas");

        Availability availability1 = new Availability(LocalDate.of(2023, 4, 1),hotel1,  5);
        Availability availability2 = new Availability(LocalDate.of(2023, 4, 2),hotel1,  2);
        Availability availability3 = new Availability(LocalDate.of(2023, 4, 3),hotel1,  2);
        Availability availability4 = new Availability(LocalDate.of(2023, 4, 4),hotel1,  3);
        Availability availability5 = new Availability(LocalDate.of(2023, 4, 5),hotel1,  4);

        Availability availability6 = new Availability(LocalDate.of(2023, 4, 1),hotel2,  2);
        Availability availability7 = new Availability(LocalDate.of(2023, 4, 2),hotel2,  1);
        Availability availability8 = new Availability(LocalDate.of(2023, 4, 3),hotel2,  2);
        Availability availability9 = new Availability(LocalDate.of(2023, 4, 4),hotel2,  3);
        Availability availability10 = new Availability(LocalDate.of(2023, 4, 5),hotel2,  4);

        List<Availability> availabilities = Arrays.asList(
                availability1, availability2, availability3, availability4, availability5,
                availability6, availability7, availability8, availability9, availability10);

        Mockito.when(availabilityRepository.findByDateBetween(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
                .thenReturn(availabilities);

        // Act
        List<Hotel> availableHotels = availabilityService.checkAvailability(
                LocalDate.of(2023, 4, 1),
                LocalDate.of(2023, 4, 3),
                "Hotel Test1",
                "3 Estrellas");

        // Assert
        Assert.assertEquals(1, availableHotels.size());
        Assert.assertEquals(hotel1.getName(), availableHotels.get(0).getName());

    }
    @Test
    public void checkAvailability_shouldReturnNoHotelsWithFilters() {
        // Arrange
        LocalDate fromDate = LocalDate.of(2023, 4, 1);
        LocalDate toDate = LocalDate.of(2023, 4, 5);

        // No hay disponibilidad para las fechas especificadas
        Availability availability1 = new Availability(fromDate, new Hotel(),  0);
        Availability availability2 = new Availability(fromDate.plusDays(1), new Hotel("Hotel Test1", "5 Estrellas"),  0);
        Availability availability3 = new Availability(fromDate.plusDays(2), new Hotel("Hotel Test2", "3 Estrellas"),  0);
        Availability availability4 = new Availability(fromDate.plusDays(3), new Hotel("Hotel Test3", "2 Estrellas"),  0);
        Availability availability5 = new Availability(fromDate.plusDays(4), new Hotel("Hotel Test4", "5 Estrellas"),  0);
        List<Availability> availabilities = Arrays.asList(
                availability1, availability2, availability3, availability4, availability5);
        Mockito.when(availabilityRepository.findByDateBetween(fromDate, toDate)).thenReturn(availabilities);

        // Act
        List<Hotel> availableHotels = availabilityService.checkAvailability(fromDate, toDate, "Hotel 1", null);

        // Assert
        Assert.assertEquals(0, availableHotels.size());
    }

}
