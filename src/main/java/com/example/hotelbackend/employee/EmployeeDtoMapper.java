package com.example.hotelbackend.employee;

import org.springframework.stereotype.Service;

@Service
public class EmployeeDtoMapper {

    EmployeeDto map(Employee employee){
        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getSurname(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getPosition().toString()
        );
    }

    Employee map(EmployeeDto employeeDto){
        Employee employee = new Employee();
        employee.setName(employeeDto.name());
        employee.setSurname(employeeDto.surname());
        employee.setEmail(employeeDto.email());
        employee.setPhoneNumber(employeeDto.phoneNumber());
        employee.setPosition(Employee.EmployeePosition.valueOf(employeeDto.position()));
        return employee;
    }
}
