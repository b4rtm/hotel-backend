package com.example.hotelbackend.booking;

import com.example.hotelbackend.review.Review;
import com.example.hotelbackend.smtp.ClientSMTP;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingDtoMapper bookingDtoMapper;
    private final BookingWithRoomDtoDtoMapper bookingWithRoomDtoDtoMapper;
    private final ClientSMTP clientSMTP;

    public BookingService(BookingRepository bookingRepository, BookingDtoMapper bookingDtoMapper, BookingWithRoomDtoDtoMapper bookingWithRoomDtoDtoMapper, ClientSMTP clientSMTP) {
        this.bookingRepository = bookingRepository;
        this.bookingDtoMapper = bookingDtoMapper;
        this.bookingWithRoomDtoDtoMapper = bookingWithRoomDtoDtoMapper;
        this.clientSMTP = clientSMTP;
    }

    Long saveBooking(BookingWithIdsDto bookingWithIdsDto){
        Booking booking = bookingDtoMapper.map(bookingWithIdsDto);
        Booking savedBooking = bookingRepository.save(booking);
        return savedBooking.getId();
    }



    public Optional<BookingDto> getBookingById(Long id){
        return bookingRepository.findById(id).map(bookingDtoMapper::map);
    }

    public List<BookingDto> getAllBookings(){
       return bookingRepository.findAll().stream().map(bookingDtoMapper::map).toList();
    }

    void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<BookingWithRoomDtoDto> getAllUserBookings(Long userId) {
        return bookingRepository.findAllByCustomerId(userId).stream().map(bookingWithRoomDtoDtoMapper::map).toList();
    }

    public void sendEmailConfirmation(Long bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        booking.ifPresent(value -> clientSMTP.sendEmail("bartek.matusiak12@gmail.com", "Potwierdzenie rezerwacji o numerze " + value.getId(), generateEmailConfirmationText(value)));
    }
    public void addReviewToBooking(Long bookingId, Review review){
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        booking.ifPresent(value -> {
            value.setReview(review);
            bookingRepository.save(value);
        });
    }

    private String generateEmailConfirmationText(Booking booking) {
        return "Cześć " +
                booking.getCustomer().getName() + "!\n\n" +
                "Dziękujemy za dokonanie rezerwacji! Oto szczegóły Twojej rezerwacji:\n\n" +
                "Numer rezerwacji: " +
                booking.getId() + "\n" +
                "Data zameldowania: " +
                booking.getCheckInDate() + "\n" +
                "Data wymeldowania: " +
                booking.getCheckOutDate() + "\n" +
                "Pokój: " +
                booking.getRoom().getName() + "\n" +
                "Cena całkowita: " +
                booking.getBookingPrice() +
                " PLN\n\n" +
                "Z niecierpliwością oczekujemy na Twoje przybycie.\n\n" +
                "Z poważaniem,\n" +
                "Zespół Royal Residence";
    }


    public Booking approveBooking(Long id) throws BookingNotFoundException {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));
        booking.setApproved(true);
        return bookingRepository.save(booking);
    }
}
