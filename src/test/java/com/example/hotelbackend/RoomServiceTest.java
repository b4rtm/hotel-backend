package com.example.hotelbackend;

import com.example.hotelbackend.booking.Booking;
import com.example.hotelbackend.booking.BookingRepository;
import com.example.hotelbackend.booking.dto.BookingDateDto;
import com.example.hotelbackend.booking.mapper.BookingDateDtoMapper;
import com.example.hotelbackend.image.ImageService;
import com.example.hotelbackend.review.ReviewService;
import com.example.hotelbackend.review.dto.ReviewDto;
import com.example.hotelbackend.review.ReviewDtoMapper;
import com.example.hotelbackend.room.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RoomDtoMapper roomDtoMapper;
    @Mock
    private ImageService imageService;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private BookingDateDtoMapper bookingDateDtoMapper;
    @Mock
    private ReviewService reviewService;
    @Mock
    private ReviewDtoMapper reviewDtoMapper;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRooms() {
        Room room = new Room();
        RoomDto roomDto = new RoomDto();
        ReviewDto reviewDto = new ReviewDto(1L, "Review", "Great", 5);

        when(roomRepository.findAll()).thenReturn(List.of(room));
        when(roomDtoMapper.map(room)).thenReturn(roomDto);
        when(reviewService.getReviewsByRoomId(room.getId())).thenReturn(List.of());
        when(reviewDtoMapper.map(any())).thenReturn(reviewDto);

        List<RoomDto> rooms = roomService.getRooms();

        assertNotNull(rooms);
        assertEquals(1, rooms.size());
        verify(roomRepository, times(1)).findAll();
        verify(roomDtoMapper, times(1)).map(room);
        verify(reviewService, times(1)).getReviewsByRoomId(room.getId());
    }

    @Test
    void testGetRoomById() {
        Long roomId = 1L;
        Room room = new Room();
        room.setId(roomId);
        RoomDto roomDto = new RoomDto();
        ReviewDto reviewDto = new ReviewDto(1L, "Review", "Great!", 5);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(roomDtoMapper.map(room)).thenReturn(roomDto);
        when(reviewService.getReviewsByRoomId(roomId)).thenReturn(List.of());
        when(reviewDtoMapper.map(any())).thenReturn(reviewDto);

        Optional<RoomDto> foundRoom = roomService.getRoomById(roomId);

        assertTrue(foundRoom.isPresent());
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomDtoMapper, times(1)).map(room);
        verify(reviewService, times(1)).getReviewsByRoomId(roomId);
    }

    @Test
    void testSaveRoom() {
        Room room = new Room();
        RoomDto roomDto = new RoomDto();
        MultipartFile image = mock(MultipartFile.class);

        when(roomDtoMapper.map(roomDto)).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(room);
        when(roomDtoMapper.map(room)).thenReturn(roomDto);

        RoomDto savedRoom = roomService.saveRoom(roomDto, List.of(image));

        assertNotNull(savedRoom);
        verify(roomDtoMapper, times(1)).map(roomDto);
        verify(roomRepository, times(1)).save(room);
        verify(imageService, times(1)).saveFile(image, room.getName(), room.getId());
    }

    @Test
    void testReplaceRoom() {
        Long roomId = 1L;
        String name = "Updated Room";
        int capacity = 4;
        int pricePerNight = 100;
        String description = "Updated description";

        Room room = new Room();
        room.setId(roomId);

        RoomDto roomDto = new RoomDto();

        MultipartFile image = mock(MultipartFile.class);

        when(roomDtoMapper.map(any(RoomDto.class))).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(room);
        when(roomDtoMapper.map(room)).thenReturn(roomDto);

        RoomDto updatedRoom = roomService.replaceRoom(roomId, name, capacity, pricePerNight, description, List.of(image));

        assertNotNull(updatedRoom);
        verify(roomDtoMapper, times(1)).map(any(RoomDto.class));
        verify(roomRepository, times(1)).save(room);
        verify(imageService, times(1)).saveFile(image, name, roomId);
    }
    @Test
    void testDeleteRoom() {
        Long roomId = 1L;
        List<String> imagePaths = List.of("image1.jpg", "image2.jpg");

        when(imageService.getAllByRoomId(roomId)).thenReturn(imagePaths);

        roomService.deleteRoom(roomId);

        verify(imageService, times(1)).getAllByRoomId(roomId);
        verify(imageService, times(1)).deleteRoomImage(roomId, "image1.jpg");
        verify(imageService, times(1)).deleteRoomImage(roomId, "image2.jpg");
        verify(roomRepository, times(1)).deleteById(roomId);
    }

    @Test
    void testGetBookingsDateForRoom() {
        Long roomId = 1L;
        BookingDateDto bookingDateDto = new BookingDateDto(LocalDate.now(), LocalDate.now().plusDays(1));
        Booking booking = new Booking();

        when(bookingRepository.findByRoomId(roomId)).thenReturn(List.of(booking));
        when(bookingDateDtoMapper.map(any())).thenReturn(bookingDateDto);

        List<BookingDateDto> bookingDates = roomService.getBookingsDateForRoom(roomId);

        assertNotNull(bookingDates);
        verify(bookingRepository, times(1)).findByRoomId(roomId);
        verify(bookingDateDtoMapper, times(1)).map(any());
    }
}
