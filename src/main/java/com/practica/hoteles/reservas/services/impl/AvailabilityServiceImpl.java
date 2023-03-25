package com.practica.hoteles.reservas.services.impl;

import com.practica.hoteles.reservas.dtos.AvailabilityRange;
import com.practica.hoteles.reservas.dtos.HotelDto;
import com.practica.hoteles.reservas.entities.Availability;
import com.practica.hoteles.reservas.entities.Hotel;
import com.practica.hoteles.reservas.exceptions.AvailabilityNotFoundException;
import com.practica.hoteles.reservas.repositories.AvailabilityRepository;
import com.practica.hoteles.reservas.repositories.BookingRepository;
import com.practica.hoteles.reservas.repositories.HotelRepository;
import com.practica.hoteles.reservas.services.AvailabilityService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Availability createAvailability(Hotel hotel, LocalDate date, int rooms) {

        // Creamos nueva entidad de Disponibilidad
        Availability availability = new Availability();
        availability.setHotel(hotel);
        availability.setDate(date);
        availability.setRooms(rooms);

        // Guardamos la entidad "Disponibilidad"
        return availabilityRepository.save(availability);
    }

    @Override
    public AvailabilityRange createAvailabilities (long hotelId, LocalDate initDate, LocalDate endDate, int rooms) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new AvailabilityNotFoundException(hotelId));
        List<Availability> availabilities = new LinkedList<>();
        LocalDate currentDate = initDate;

        while (!currentDate.isAfter(endDate)) {
            Availability availability = availabilityRepository.findByHotelIdAndDate(hotelId, currentDate);
            if (availability == null) {
                // If availability does not exist for the current date, create a new one with the given number of rooms
                availability = createAvailability(hotel, currentDate, rooms);
            } else {
                // If availability already exists for the current date, add the given number of rooms to it
                availability.setRooms(availability.getRooms() + rooms);
            }
            // Save the availability
            availabilityRepository.save(availability);
            // Add the availability to the list
            availabilities.add(availability);
            // Move to the next date
            currentDate = currentDate.plusDays(1);
        }
        return new AvailabilityRange(HotelDto.hotelToDto(hotel), initDate, endDate, rooms);

    }
    @Override
    public Availability getAvailabilityByHotelAndDate(long hotelId, LocalDate date) {

        Availability availability = availabilityRepository.findByHotelIdAndDate(hotelId, date);
        return availability;
    }

    @Override
    public List<Hotel> checkAvailability(LocalDate fromDate, LocalDate toDate, String hotelName, String hotelCategory) {
        List<Availability> availabilities = availabilityRepository.findByDateBetween(fromDate, toDate);

        // Agrupar las disponibilidades por hotel y fecha
        Map<Hotel, List<Availability>> availabilitiesByHotel =
                availabilities.stream().collect(Collectors.groupingBy(Availability::getHotel));

        // Filtrar los hoteles que tengan disponibilidad en todas las fechas y cumplan con los criterios de búsqueda
        return availabilitiesByHotel.entrySet().stream()
                .filter(hotelEntry ->
                        (hotelName == null || hotelEntry.getKey().getName().equals(hotelName)) &&
                                (hotelCategory == null || hotelEntry.getKey().getCategory().equals(hotelCategory)) &&
                                LongStream.rangeClosed(0L, ChronoUnit.DAYS.between(fromDate, toDate))
                                        .allMatch(i ->
                                                hotelEntry.getValue().stream()
                                                        .anyMatch(avail -> avail.getDate().equals(fromDate.plusDays(i)) && avail.getRooms() > 0)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

