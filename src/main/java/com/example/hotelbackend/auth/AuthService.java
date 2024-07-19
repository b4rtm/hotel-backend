package com.example.hotelbackend.auth;

import com.example.hotelbackend.auth.verification_token.AccountNotActivatedException;
import com.example.hotelbackend.customer.Customer;
import com.example.hotelbackend.customer.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;

    public AuthService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono " + email));
        if (!customer.isEnabled()) {
            throw new AccountNotActivatedException("Konto nie zostało aktywowane");
        }
        return new org.springframework.security.core.userdetails.User(
                customer.getEmail(),
                customer.getPassword(),
                customer.getAuthorities()
        );
    }
}
