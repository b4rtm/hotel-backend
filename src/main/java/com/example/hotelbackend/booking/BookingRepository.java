package com.example.hotelbackend.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByRoomId(Long roomId);

    @Query("SELECT DISTINCT booking FROM Booking booking " +
            "JOIN  booking.customer " +
            "JOIN  booking.room")
    List<Booking> findAllBookingsWithCustomerAndRoom();
}
