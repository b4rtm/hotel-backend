package com.example.hotelbackend.review;

import com.example.hotelbackend.booking.BookingService;
import com.example.hotelbackend.customer.Customer;
import com.example.hotelbackend.customer.CustomerService;
import com.example.hotelbackend.review.dto.ReviewRequestDto;
import com.example.hotelbackend.room.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingService bookingService;
    private final ReviewDtoMapper reviewDtoMapper;
    private final CustomerService customerService;
    private final RoomRepository roomRepository;

    public ReviewService(ReviewRepository reviewRepository, @Lazy BookingService bookingService, ReviewDtoMapper reviewDtoMapper, CustomerService customerService, RoomRepository roomRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingService = bookingService;
        this.reviewDtoMapper = reviewDtoMapper;
        this.customerService = customerService;
        this.roomRepository = roomRepository;
    }

     Review addReview(ReviewRequestDto reviewRequest) {
        Optional<Customer> customer = customerService.getCustomerById(reviewRequest.customerId());
        String name = customer.map(Customer::getName).orElse("Bartek");
        Review review = reviewDtoMapper.map(reviewRequest);
        review.setName(name);
        Room room = roomRepository.findById(reviewRequest.roomId()).orElseThrow(() -> new RoomNotFoundException("Room not found with id:" + reviewRequest.roomId()));
        review.setRoom(room);
        Review saved = reviewRepository.save(review);
        bookingService.addReviewToBooking(reviewRequest.bookingId(), saved);
        return saved;
    }

    public List<Review> getReviewsByRoomId(Long roomId){
        return reviewRepository.findAllByRoomId(roomId);
    }
}
