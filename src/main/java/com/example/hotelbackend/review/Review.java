package com.example.hotelbackend.review;

import com.example.hotelbackend.room.Room;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String comment;
    private Integer rating;

    @ManyToOne
    private Room room;

}
