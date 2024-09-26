package com.example.hotelbackend.schedule;

import java.time.LocalDateTime;
import java.util.List;

public record SendSchedulesEmailRequest(List<Long> employeeIds, LocalDateTime startDate) {
}
