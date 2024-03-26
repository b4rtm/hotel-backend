package com.example.hotelbackend.booking;

import com.example.hotelbackend.booking.date.BookingDateDto;
import com.example.hotelbackend.booking.date.BookingDateDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingDtoMapper bookingDtoMapper;
    private final BookingDateDtoMapper bookingDateDtoMapper;

    public BookingService(BookingRepository bookingRepository, BookingDtoMapper bookingDtoMapper, BookingDateDtoMapper bookingDateDtoMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingDtoMapper = bookingDtoMapper;
        this.bookingDateDtoMapper = bookingDateDtoMapper;
    }

    Long saveBooking(BookingWithIdsDto bookingWithIdsDto){
        Booking booking = bookingDtoMapper.map(bookingWithIdsDto);
        Booking savedBooking = bookingRepository.save(booking);
        return savedBooking.getId();
    }

    public List<BookingDateDto> getBookingsDateForRoom(Long id){
        return bookingRepository.findByRoomId(id).stream().map(bookingDateDtoMapper::map).toList();
    }

    @Transactional
    public List<BookingDto> getAllBookings(){
       return bookingRepository.findAllBookingsWithCustomerAndRoom().stream().map(bookingDtoMapper::map).toList();
    }

    void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
