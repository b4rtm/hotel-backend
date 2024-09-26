package com.example.hotelbackend.schedule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    ResponseEntity<List<Schedule>> getSchedules(){
        List<Schedule> schedules = scheduleService.getSchedules();
        if (schedules.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(schedules);
    }

    @PostMapping
    ResponseEntity<Schedule> addSchedule(@RequestBody Schedule schedule){
        return ResponseEntity.ok(scheduleService.createSchedule(schedule));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.replaceSchedule(id, schedule));
    }

    @PostMapping("/emails")
    ResponseEntity<String> sendSchedulesByEmail(@RequestBody SendSchedulesEmailRequest emailRequest){
        for(Long id : emailRequest.employeeIds()) {
            scheduleService.sendScheduleToEmployee(id, emailRequest.startDate());
        }
        return ResponseEntity.ok("Emails sent successfully");
    }

}
