package com.example.hotelbackend.schedule;

import com.example.hotelbackend.employee.Employee;
import com.example.hotelbackend.employee.EmployeeService;
import com.example.hotelbackend.smtp.ClientSMTP;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmployeeService employeeService;
    private final ClientSMTP clientSMTP;

    public ScheduleService(ScheduleRepository scheduleRepository, EmployeeService employeeService, ClientSMTP clientSMTP) {
        this.scheduleRepository = scheduleRepository;
        this.employeeService = employeeService;
        this.clientSMTP = clientSMTP;
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


    public void sendScheduleToEmployee(Long id, LocalDateTime startDate) {
        Employee employee = employeeService.getEmployeeById(id);
        List<Schedule> schedules = scheduleRepository.findByEmployeeIdAndStartDateGreaterThanEqual(id, startDate);
        String subject = "Grafik dla " + employee.getName() + " " + employee.getSurname();

        StringBuilder text = new StringBuilder("Oto twój grafik od dnia " + startDate.toString().substring(0,10) + ":\n");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Schedule schedule : schedules) {
            text.append(schedule.getTitle()).append(": ")
                    .append(schedule.getStartDate().format(dateFormatter)).append(" - ")
                    .append(schedule.getEndDate().format(timeFormatter)).append("\n\n");
        }

        clientSMTP.sendEmail(employee.getEmail(), subject, text.toString());
    }
}
