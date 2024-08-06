package com.example.hotelbackend.review;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private String name;
    private String comment;
    private Integer rating;
}
