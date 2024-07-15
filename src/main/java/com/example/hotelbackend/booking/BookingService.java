package com.example.hotelbackend.booking;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingDtoMapper bookingDtoMapper;
    private final BookingWithRoomDtoDtoMapper bookingWithRoomDtoDtoMapper;

    public BookingService(BookingRepository bookingRepository, BookingDtoMapper bookingDtoMapper, BookingWithRoomDtoDtoMapper bookingWithRoomDtoDtoMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingDtoMapper = bookingDtoMapper;
        this.bookingWithRoomDtoDtoMapper = bookingWithRoomDtoDtoMapper;
    }

    Long saveBooking(BookingWithIdsDto bookingWithIdsDto){
        Booking booking = bookingDtoMapper.map(bookingWithIdsDto);
        Booking savedBooking = bookingRepository.save(booking);
        return savedBooking.getId();
    }



    public Optional<BookingDto> getBookingById(Long id){
        return bookingRepository.findById(id).map(bookingDtoMapper::map);
    }

    public List<BookingDto> getAllBookings(){
       return bookingRepository.findAll().stream().map(bookingDtoMapper::map).toList();
    }

    void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<BookingWithRoomDtoDto> getAllUserBookings(Long userId) {
        return bookingRepository.findAllByCustomerId(userId).stream().map(bookingWithRoomDtoDtoMapper::map).toList();
    }
}
