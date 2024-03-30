package com.example.hotelbackend.customer;

import com.example.hotelbackend.config.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public CustomerController(CustomerService customerService, AuthService authService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.customerService = customerService;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customer){
        CustomerDto savedCustomer = customerService.saveCustomer(customer);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCustomer.getId()).toUri();
        return ResponseEntity.created(uri).body(savedCustomer);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        UserDetails userDetails = authService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(token);
    }

    @GetMapping("/users")
    ResponseEntity<List<CustomerDto>> getUsers(){
        List<CustomerDto> users = customerService.getUsers();
        if(users.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{id}")
    ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto){
        return ResponseEntity.ok(customerService.replaceCustomer(id, customerDto));
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
