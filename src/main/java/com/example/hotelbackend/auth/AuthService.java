package com.example.hotelbackend.auth;

import com.example.hotelbackend.auth.dto.IdTokenRequestDto;
import com.example.hotelbackend.auth.verification_token.AccountNotActivatedException;
import com.example.hotelbackend.config.JwtUtil;
import com.example.hotelbackend.customer.Customer;
import com.example.hotelbackend.customer.CustomerRepository;
import com.example.hotelbackend.customer.CustomerService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final GoogleIdTokenVerifier verifier;
    private final JwtUtil jwtUtils;


    public AuthService(@Value("${app.googleClientId}") String clientId, CustomerRepository customerRepository, @Lazy CustomerService customerService, JwtUtil jwtUtils) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.jwtUtils = jwtUtils;
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();
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

    public String loginOAuthGoogle(IdTokenRequestDto requestBody) {
        Customer customer = verifyIDToken(requestBody.getIdToken());
        if (customer == null) {
            throw new IllegalArgumentException();
        }
        customer = customerService.createGoogleCustomerIfNotExist(customer);
        return jwtUtils.generateToken(loadUserByUsername(customer.getEmail()));
    }

    private Customer verifyIDToken(String idToken) {
        try {
            GoogleIdToken idTokenObj = verifier.verify(idToken);
            if (idTokenObj == null) {
                return null;
            }
            GoogleIdToken.Payload payload = idTokenObj.getPayload();
            String firstName = (String) payload.get("given_name");
            String lastName = (String) payload.get("family_name");
            String email = payload.getEmail();

            Customer customer = new Customer();
            customer.setEmail(email);
            customer.setName(firstName);
            customer.setPassword("");
            customer.setSurname(lastName);
            customer.setRole(Customer.Role.ROLE_USER);
            return customer;
        } catch (GeneralSecurityException | IOException e) {
            return null;
        }
    }

}
