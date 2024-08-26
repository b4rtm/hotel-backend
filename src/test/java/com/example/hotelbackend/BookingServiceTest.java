package com.example.hotelbackend;

import com.example.hotelbackend.booking.Booking;
import com.example.hotelbackend.booking.BookingRepository;
import com.example.hotelbackend.booking.BookingService;
import com.example.hotelbackend.booking.dto.BookingDto;
import com.example.hotelbackend.booking.dto.BookingWithIdsDto;
import com.example.hotelbackend.booking.mapper.BookingDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingDtoMapper bookingDtoMapper;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBooking() {
        BookingWithIdsDto bookingWithIdsDto = new BookingWithIdsDto(LocalDate.now(), LocalDate.now().plusDays(1), 1L, 1L);
        Booking booking = new Booking();
        booking.setId(1L);
        when(bookingDtoMapper.map(bookingWithIdsDto)).thenReturn(booking);
        when(bookingRepository.save(booking)).thenReturn(booking);

        Long bookingId = bookingService.saveBooking(bookingWithIdsDto);

        assertEquals(1L, bookingId);
        verify(bookingRepository).save(booking);
    }

    @Test
    void testGetBookingById() {
        Long bookingId = 1L;
        Booking booking = new Booking();
        BookingDto bookingDto = new BookingDto(bookingId, LocalDate.now(), LocalDate.now().plusDays(1), null, null, true);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingDtoMapper.map(booking)).thenReturn(bookingDto);

        Optional<BookingDto> result = bookingService.getBookingById(bookingId);

        assertEquals(Optional.of(bookingDto), result);
        verify(bookingRepository).findById(bookingId);
    }

    @Test
    void testGetAllBookings() {
        List<Booking> bookings = Collections.singletonList(new Booking());
        List<BookingDto> bookingDtos = Collections.singletonList(new BookingDto(1L, LocalDate.now(), LocalDate.now().plusDays(1), null, null, true));
        when(bookingRepository.findAll()).thenReturn(bookings);
        when(bookingDtoMapper.map(any(Booking.class))).thenReturn(bookingDtos.get(0));

        List<BookingDto> result = bookingService.getAllBookings();

        assertEquals(bookingDtos, result);
        verify(bookingRepository).findAll();
    }

    @Test
    void testDeleteBooking() {
        Long bookingId = 1L;

        bookingService.deleteBooking(bookingId);

        verify(bookingRepository).deleteById(bookingId);
    }

}
