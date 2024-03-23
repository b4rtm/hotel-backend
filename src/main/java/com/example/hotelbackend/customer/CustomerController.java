package com.example.hotelbackend.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping("/register")
    ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customer){
        CustomerDto savedCustomer = customerService.saveCustomer(customer);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCustomer.getId()).toUri();
        return ResponseEntity.created(uri).body(savedCustomer);
    }

    @GetMapping("/users")
    ResponseEntity<List<CustomerDto>> getUsers(){
        List<CustomerDto> users = customerService.getUsers();
        if(users.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(users);
    }


}
