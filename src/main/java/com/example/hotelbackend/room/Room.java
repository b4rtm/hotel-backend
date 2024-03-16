package com.example.hotelbackend.room;

import com.example.hotelbackend.booking.Booking;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int capacity;
    private int pricePerNight;
    private String imagePath;
    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

}
