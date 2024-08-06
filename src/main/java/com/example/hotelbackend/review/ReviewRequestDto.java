package com.example.hotelbackend.review;

import lombok.Data;

@Data
public class ReviewRequestDto {
    private Long bookingId;
    private Long customerId;
    private Long roomId;
    private String comment;
    private Integer rating;
}
