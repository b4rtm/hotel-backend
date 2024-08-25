package com.example.hotelbackend.booking;

import com.example.hotelbackend.booking.dto.BookingDto;
import com.example.hotelbackend.booking.dto.BookingWithIdsDto;
import com.example.hotelbackend.booking.dto.BookingWithRoomDto;
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
    public ResponseEntity<String> bookRoom(@RequestBody BookingWithIdsDto booking){
        Long bookingId = bookingService.saveBooking(booking);
        bookingService.sendEmailConfirmation(bookingId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bookingId).toUri();
        return ResponseEntity.created(uri).body(bookingId.toString());
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getBookings(){
        List<BookingDto> bookingDtoList = bookingService.getAllBookings();
        if(bookingDtoList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookingDtoList);
    }

    @GetMapping("/user")
    public ResponseEntity<List<BookingWithRoomDto>> getUserBookings(@RequestParam Long userId){
        List<BookingWithRoomDto> bookingDtoList = bookingService.getAllUserBookings(userId);
        if(bookingDtoList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookingDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id){
        return bookingService.getBookingById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<Booking> approveBooking(@PathVariable Long id) {
        Booking updatedBooking = bookingService.approveBooking(id);
        return ResponseEntity.ok(updatedBooking);
    }
}
