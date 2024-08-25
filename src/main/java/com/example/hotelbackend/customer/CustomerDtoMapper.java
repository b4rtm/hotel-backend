package com.example.hotelbackend.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerDtoMapper {

    Customer map(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setPesel(dto.pesel());
        customer.setSurname(dto.surname());
        customer.setEmail(dto.email());
        customer.setPhoneNumber(dto.phoneNumber());
        customer.setAddress(dto.address());
        customer.setCity(dto.city());
        customer.setPostCode(dto.postCode());
        customer.setRole(dto.role());
        customer.setPassword(dto.password());
        return customer;
    }

    CustomerDto map(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                customer.getEmail(),
                customer.getPesel(),
                customer.getAddress(),
                customer.getCity(),
                customer.getPostCode(),
                customer.getPassword(),
                customer.getPhoneNumber(),
                customer.getRole()
        );
    }
}
