package com.example.hotelbackend.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByRoomId(Long roomId);

    List<Booking> findAllByCustomerId(Long customerId);
}
