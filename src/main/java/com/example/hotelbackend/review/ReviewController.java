package com.example.hotelbackend.review;

import com.example.hotelbackend.review.dto.ReviewRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewRequestDto reviewRequest){
        Review review = reviewService.addReview(reviewRequest);
        return ResponseEntity.ok(review);
    }

}
