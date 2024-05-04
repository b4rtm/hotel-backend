package com.example.hotelbackend.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerDtoMapper {

    Customer map(CustomerDto dto){
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setPesel(dto.getPesel());
        customer.setSurname(dto.getSurname());
        customer.setEmail(dto.getEmail());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setAddress(dto.getAddress());
        customer.setCity(dto.getCity());
        customer.setPostCode(dto.getPostCode());
        customer.setRole(dto.getRole());
        customer.setPassword(dto.getPassword());
        return customer;
    }

    CustomerDto map(Customer customer){
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPesel(customer.getPesel());
        dto.setSurname(customer.getSurname());
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setAddress(customer.getAddress());
        dto.setCity(customer.getCity());
        dto.setPostCode(customer.getPostCode());
        dto.setPassword(customer.getPassword());
        dto.setRole(customer.getRole());
        return dto;
    }
}
