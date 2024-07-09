package com.example.hotelbackend.image;

import com.example.hotelbackend.room.Room;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

    @ManyToOne
    private Room room;

}
