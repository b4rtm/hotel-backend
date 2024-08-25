package com.example.hotelbackend.customer;

public record CustomerDto(
        Long id,
        String name,
        String surname,
        String email,
        String pesel,
        String address,
        String city,
        String postCode,
        String password,
        String phoneNumber,
        Customer.Role role
) {}