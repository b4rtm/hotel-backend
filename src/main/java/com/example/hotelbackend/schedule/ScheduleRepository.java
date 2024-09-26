package com.example.hotelbackend.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByEmployeeIdAndStartDateGreaterThanEqual(Long employeeId, LocalDateTime startDate);

}
