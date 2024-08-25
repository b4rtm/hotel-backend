package com.example.hotelbackend.review.dto;


public record ReviewRequestDto(Long bookingId,Long customerId, Long roomId, String comment, Integer rating) {

}
