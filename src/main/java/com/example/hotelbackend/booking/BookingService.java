package com.example.hotelbackend.booking;

import com.example.hotelbackend.smtp.ClientSMTP;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
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
        if(booking.isPresent()){
            clientSMTP.sendEmail("bartek.matusiak12@gmail.com", "Potwierdzenie rezerwacji o numerze " + booking.get().getId(), generateEmailConfirmationText(booking.get()));
        }
    }

    private String generateEmailConfirmationText(Booking booking) {
        StringBuilder emailText = new StringBuilder();

        emailText.append("Cześć ")
                .append(booking.getCustomer().getName())
                .append("!\n\n")
                .append("Dziękujemy za dokonanie rezerwacji! Oto szczegóły Twojej rezerwacji:\n\n")
                .append("Numer rezerwacji: ")
                .append(booking.getId())
                .append("\n")
                .append("Data zameldowania: ")
                .append(booking.getCheckInDate())
                .append("\n")
                .append("Data wymeldowania: ")
                .append(booking.getCheckOutDate())
                .append("\n")
                .append("Pokój: ")
                .append(booking.getRoom().getName())
                .append("\n")
                .append("Cena całkowita: ")
                .append(booking.getBookingPrice())
                .append(" PLN\n\n")
                .append("Z niecierpliwością oczekujemy na Twoje przybycie.\n\n")
                .append("Z poważaniem,\n")
                .append("Zespół Royal Residence");

        return emailText.toString();
    }


}
