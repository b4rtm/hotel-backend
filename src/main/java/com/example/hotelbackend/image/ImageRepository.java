package com.example.hotelbackend.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByRoomId(Long id);

    void deleteByPathAndRoomId(String path, Long id);

}
