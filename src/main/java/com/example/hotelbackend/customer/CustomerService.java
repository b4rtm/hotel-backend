package com.example.hotelbackend.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private CustomerDtoMapper customerDtoMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoMapper customerDtoMapper) {
        this.customerRepository = customerRepository;
        this.customerDtoMapper = customerDtoMapper;
    }

    CustomerDto saveCustomer(CustomerDto dto){
        Customer customer = customerDtoMapper.map(dto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerDtoMapper.map(savedCustomer);
    }
}
