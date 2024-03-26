package com.example.hotelbackend.booking;

import com.example.hotelbackend.customer.Customer;
import com.example.hotelbackend.customer.CustomerRepository;
import com.example.hotelbackend.room.Room;
import com.example.hotelbackend.room.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class BookingDtoMapper {

    private final RoomRepository roomRepository;
    private final CustomerRepository customerRepository;

    public BookingDtoMapper(RoomRepository roomRepository, CustomerRepository customerRepository) {
        this.roomRepository = roomRepository;
        this.customerRepository = customerRepository;
    }

    Booking map(BookingWithIdsDto dto) {
        Booking booking = new Booking();
        long differenceInDays = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
        Room room = roomRepository.findById(dto.getRoomId()).orElseThrow(() -> new IllegalStateException("Room not found"));
        Customer customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new IllegalStateException("Customer not found"));

        booking.setBookingPrice((int) (differenceInDays * room.getPricePerNight()));
        booking.setRoom(room);
        booking.setCheckInDate(dto.getCheckInDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        booking.setCustomer(customer);
        return booking;
    }

    BookingDto map(Booking booking){
        BookingDto bookingDto = new BookingDto();
        bookingDto.setCustomer(booking.getCustomer());
        bookingDto.setRoom(booking.getRoom());
        bookingDto.setCheckOutDate(booking.getCheckOutDate());
        bookingDto.setCheckInDate(booking.getCheckInDate());
        return bookingDto;
    }
}
