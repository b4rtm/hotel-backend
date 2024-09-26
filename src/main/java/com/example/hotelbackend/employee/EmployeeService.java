package com.example.hotelbackend.employee;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeDtoMapper employeeDtoMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeDtoMapper employeeDtoMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeDtoMapper = employeeDtoMapper;
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee saved = employeeRepository.save(employeeDtoMapper.map(employeeDto));
        return employeeDtoMapper.map(saved);
    }

    public List<EmployeeDto> getEmployees() {
        return employeeRepository.findAll().stream().map(employeeDtoMapper::map).toList();
    }

    public Employee getEmployeeById(Long id){
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " +  id));
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    EmployeeDto replaceEmployee(Long employeeId, EmployeeDto employeeDto){
        employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));
        Employee employee = employeeDtoMapper.map(employeeDto);
        employee.setId(employeeId);
        Employee updatedEmployee = employeeRepository.save(employee);
        return employeeDtoMapper.map(updatedEmployee);
    }
}
