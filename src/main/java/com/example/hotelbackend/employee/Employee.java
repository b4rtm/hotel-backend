package com.example.hotelbackend.employee;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private EmployeePosition position;

    public enum EmployeePosition {
        HOUSEKEEPER, // Pokojówka
        RECEPTIONIST, // Recepcjonista
        COOK, // Kucharz
        SECURITY // Ochrona
    }
}
