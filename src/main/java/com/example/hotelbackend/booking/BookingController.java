package com.example.hotelbackend.booking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    ResponseEntity<String> bookRoom(@RequestBody BookingWithIdsDto booking){
        Long bookingId = bookingService.saveBooking(booking);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bookingId).toUri();
        return ResponseEntity.created(uri).body("Success");
    }

    @GetMapping
    ResponseEntity<List<BookingDto>> getBookings(){
        List<BookingDto> bookingDtoList = bookingService.getAllBookings();
        if(bookingDtoList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookingDtoList);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
