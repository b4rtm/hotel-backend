package com.example.hotelbackend.review;

import com.example.hotelbackend.review.dto.ReviewDto;
import com.example.hotelbackend.review.dto.ReviewRequestDto;
import org.springframework.stereotype.Service;

@Service
public class ReviewDtoMapper {

    Review map(ReviewRequestDto reviewDto){
        Review review = new Review();
        review.setRating(reviewDto.rating());
        review.setComment(reviewDto.comment());

        return review;
    }

    public ReviewDto map(Review review){
        return new ReviewDto(
                review.getId(),
                review.getName(),
                review.getComment(),
                review.getRating()
        );
    }
}
