package com.practica.hoteles.reservas.serviceTest;

import com.practica.hoteles.reservas.entities.Availability;
import com.practica.hoteles.reservas.entities.Booking;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.exceptions.BookingNotFoundException;
import com.practica.hoteles.reservas.exceptions.HotelNotFoundException;
import com.practica.hoteles.reservas.exceptions.NotAvailableException;
import com.practica.hoteles.reservas.repositories.AvailabilityRepository;
import com.practica.hoteles.reservas.repositories.BookingRepository;
import com.practica.hoteles.reservas.repositories.HotelRepository;
import com.practica.hoteles.reservas.services.impl.BookingServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceImplTest {

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    public void createBooking_shouldCreateBooking() {
        // Arrange
        Hotel hotel = new Hotel("Test Hotel", "Category A");
        LocalDate initDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(3);
        String email = "test@test.com";
        Booking booking = new Booking(hotel, initDate, endDate, email);

        Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenReturn(booking);

        // Act
        Booking result = bookingService.createBooking(hotel, initDate, endDate, email);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(hotel, result.getHotel());
        Assert.assertEquals(initDate, result.getDateFrom());
        Assert.assertEquals(endDate, result.getDateTo());
        Assert.assertEquals(email, result.getEmail());
    }

    @Test(expected = HotelNotFoundException.class)
    public void createBookings_withInvalidHotel_shouldThrowException() throws NotAvailableException, HotelNotFoundException {
        // Arrange
        long hotelId = 1L;
        LocalDate fromDate = LocalDate.now().plusDays(1);
        LocalDate toDate = LocalDate.now().plusDays(3);
        String email = "test@test.com";

        Mockito.when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        // Act
        bookingService.createBookings(hotelId, fromDate, toDate, email);

        // Assert throws HotelNotFoundException
    }

    @Test(expected = NotAvailableException.class)
    public void createBookings_withNotAvailableAvailability_shouldThrowException() throws NotAvailableException, HotelNotFoundException {
        // Arrange
        long hotelId = 1L;
        LocalDate fromDate = LocalDate.now().plusDays(1);
        LocalDate toDate = LocalDate.now().plusDays(3);
        String email = "test@test.com";
        Hotel hotel = new Hotel("Test Hotel", "Category A");

        Mockito.when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        Mockito.when(availabilityRepository.findByHotelIdAndDate(Mockito.anyLong(), Mockito.any(LocalDate.class)))
                .thenReturn(null); // no availability

        // Act
        bookingService.createBookings(hotelId, fromDate, toDate, email);

        // Assert throws NotAvailableException
    }
    @Test
    public void createBookings_shouldCreateBookings() throws NotAvailableException, HotelNotFoundException {
        // Arrange
        long hotelId = 1L;
        LocalDate fromDate = LocalDate.of(2022, 1, 1);
        LocalDate toDate = LocalDate.of(2022, 1, 3);
        String email = "john.doe@example.com";

        Hotel hotel = new Hotel("Hotel Test", "Category Test");
        hotel.setId(hotelId);
        Availability availability1 = new Availability(fromDate, hotel, 10);
        Availability availability2 = new Availability(fromDate.plusDays(1), hotel,10);
        Availability availability3 = new Availability(fromDate.plusDays(2), hotel,10);
//        List<Availability> availabilities = Arrays.asList(availability1, availability2, availability3);

        Booking booking = new Booking(hotel, fromDate, toDate, email);
        Mockito.when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        Mockito.when(availabilityRepository.findByHotelIdAndDate(hotelId, fromDate)).thenReturn(availability1);
        Mockito.when(availabilityRepository.findByHotelIdAndDate(hotelId, fromDate.plusDays(1))).thenReturn(availability2);
        Mockito.when(availabilityRepository.findByHotelIdAndDate(hotelId, fromDate.plusDays(2))).thenReturn(availability3);
        Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenReturn(booking);

        // Act
        Booking result = bookingService.createBookings(hotelId, fromDate, toDate, email);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(hotel, result.getHotel());
        Assert.assertEquals(fromDate, result.getDateFrom());
        Assert.assertEquals(toDate, result.getDateTo());
        Assert.assertEquals(email, result.getEmail());
        Mockito.verify(hotelRepository).findById(hotelId);
        Mockito.verify(availabilityRepository, Mockito.times(3)).
                findByHotelIdAndDate(Mockito.anyLong(), Mockito.any(LocalDate.class));
        Mockito.verify(bookingRepository).save(Mockito.any(Booking.class));
        Mockito.verify(availabilityRepository, Mockito.times(3)).
                save(Mockito.any(Availability.class));
    }
    @Test
    public void getBookingsByHotelIdAndDate_shouldReturnListOfBookings() {
        // Arrange
        long hotelId = 1L;
        LocalDate initDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);

        List<Booking> expectedBookings = Arrays.asList(
                new Booking(new Hotel("Hotel A", "3 Estrellas"),
                        initDate, endDate, "john@example.com"),
                new Booking(new Hotel("Hotel B", "4 Estrellas"),
                        initDate, endDate, "jane@example.com")
        );

        Mockito.when(bookingRepository.findByHotelIdAndDateToAfterAndDateFromBefore(hotelId, initDate, endDate))
                .thenReturn(expectedBookings);

        // Act
        List<Booking> actualBookings = bookingService.getBookingsByHotelIdAndDate(hotelId, initDate, endDate);

        // Assert
        Assert.assertEquals(expectedBookings, actualBookings);
    }

    @Test
    public void getBookingById_shouldReturnBooking() throws BookingNotFoundException {
        // Given
        Long bookingId = 1L;
        Booking expectedBooking = new Booking(
                new Hotel("Hotel A", "3 Estrellas"),
                LocalDate.now(),
                LocalDate.now().plusDays(5), "john@example.com");

        Mockito.when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(expectedBooking));

        // When
        Booking actualBooking = bookingService.getBookingById(bookingId);

        // Then
        Assert.assertEquals(expectedBooking, actualBooking);
    }

    @Test
    public void getBookingById_shouldThrowBookingNotFoundException() {
        // Arrange
        Long bookingId = 1L;

        Mockito.when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // Act and Assert
        Assert.assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingById(bookingId));
    }
    @Test
    public void cancelBooking_shouldCancelBooking() throws BookingNotFoundException {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        LocalDate fromDate = LocalDate.of(2023, 4, 1);
        LocalDate toDate = LocalDate.of(2023, 4, 5);
        Booking booking = new Booking(hotel, fromDate, toDate, "test@test.com");
        booking.setId(1L);

        Availability availability = new Availability();
        availability.setRooms(0);

        Mockito.when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        Mockito.when(availabilityRepository.findByHotelIdAndDate(Mockito.anyLong(), Mockito.any(LocalDate.class)))
                .thenReturn(availability);

        // Act
        bookingService.cancelBooking(1L);

        // Assert
        Mockito.verify(availabilityRepository, Mockito.times(5)).save(Mockito.any(Availability.class));
        Mockito.verify(bookingRepository, Mockito.times(1)).delete(Mockito.any(Booking.class));
    }

    @Test
    public void cancelBooking_shouldThrowBookingNotFoundException() {
        // Arrange
        Mockito.when(bookingRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        Assert.assertThrows(BookingNotFoundException.class,
                () -> bookingService.cancelBooking(1L));
    }
}
