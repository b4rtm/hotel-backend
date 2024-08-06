package com.example.hotelbackend.review;

import org.springframework.stereotype.Service;

@Service
public class ReviewDtoMapper {

    Review map(ReviewRequestDto reviewDto){
        Review review = new Review();
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        return review;
    }

    public ReviewDto map(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setName(review.getName());
        reviewDto.setComment(review.getComment());
        reviewDto.setRating(review.getRating());
        return reviewDto;
    }
}
