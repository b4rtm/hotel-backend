package com.example.hotelbackend.schedule;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    Schedule replaceSchedule(Long scheduleId, Schedule schedule){
        scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotFoundException("Employee not found with id: " + scheduleId));
        schedule.setId(scheduleId);
        return scheduleRepository.save(schedule);
    }
}
