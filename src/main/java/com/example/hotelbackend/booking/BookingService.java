package com.example.hotelbackend.booking;

import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingDtoMapper bookingDtoMapper;

    public BookingService(BookingRepository bookingRepository, BookingDtoMapper bookingDtoMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingDtoMapper = bookingDtoMapper;
    }

    Long saveBooking(BookingDto bookingDto){
        Booking booking = bookingDtoMapper.map(bookingDto);
        Booking savedBooking = bookingRepository.save(booking);
        return savedBooking.getId();
    }
}
