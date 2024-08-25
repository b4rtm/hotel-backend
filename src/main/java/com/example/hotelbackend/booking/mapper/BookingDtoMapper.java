package com.example.hotelbackend.booking.mapper;

import com.example.hotelbackend.booking.Booking;
import com.example.hotelbackend.booking.dto.BookingDto;
import com.example.hotelbackend.booking.dto.BookingWithIdsDto;
import com.example.hotelbackend.customer.Customer;
import com.example.hotelbackend.customer.CustomerNotFoundException;
import com.example.hotelbackend.customer.CustomerRepository;
import com.example.hotelbackend.room.Room;
import com.example.hotelbackend.room.RoomNotFoundException;
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

    public Booking map(BookingWithIdsDto dto) {
        long differenceInDays = ChronoUnit.DAYS.between(dto.checkInDate(), dto.checkOutDate());
        Room room = roomRepository.findById(dto.roomId()).orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + dto.roomId()));
        Customer customer = customerRepository.findById(dto.customerId()).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + dto.customerId()));

        Booking booking = new Booking();
        booking.setBookingPrice((int) (differenceInDays * room.getPricePerNight()));
        booking.setRoom(room);
        booking.setCheckInDate(dto.checkInDate());
        booking.setCheckOutDate(dto.checkOutDate());
        booking.setCustomer(customer);
        return booking;
    }

    public BookingDto map(Booking booking) {
        return new BookingDto(booking.getId(), booking.getCheckInDate(), booking.getCheckOutDate(), booking.getCustomer(), booking.getRoom(), booking.isApproved());
    }
}
