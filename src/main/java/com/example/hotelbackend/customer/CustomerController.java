package com.example.hotelbackend.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping
    ResponseEntity<List<CustomerDto>> getUsers() {
        List<CustomerDto> users = customerService.getUsers();
        if (users.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getUser")
    ResponseEntity<CustomerDto> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return customerService.getCustomerByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.replaceCustomer(id, customerDto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
