package com.example.hotelbackend.customer;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoMapper customerDtoMapper;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoMapper customerDtoMapper, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.customerDtoMapper = customerDtoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    CustomerDto saveCustomer(CustomerDto dto){
        Customer customer = customerDtoMapper.map(dto);
        String passwordHash = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(passwordHash);
        Customer savedCustomer = customerRepository.save(customer);
        return customerDtoMapper.map(savedCustomer);
    }
}
