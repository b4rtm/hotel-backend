package com.example.hotelbackend.auth;

import com.example.hotelbackend.auth.dto.AuthenticationRequestDto;
import com.example.hotelbackend.auth.dto.IdTokenRequestDto;
import com.example.hotelbackend.config.JwtUtil;
import com.example.hotelbackend.customer.CustomerDto;
import com.example.hotelbackend.customer.CustomerService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CustomerService customerService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(CustomerService customerService, AuthService authService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
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

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return customerService.confirmToken(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthenticationRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user unauthorized");
        }
        UserDetails userDetails = authService.loadUserByUsername(request.username());
        String token = jwtUtil.generateToken(userDetails);

        JSONObject tokenResponse;
        try {
            tokenResponse = new JSONObject("{\"token\": \"" + token + "\"}");
        } catch (JSONException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("json problem");
        }
        return ResponseEntity.ok(tokenResponse.toString());
    }

    @PostMapping("/google-login")
    public ResponseEntity<String> LoginWithGoogleOauth2(@RequestBody IdTokenRequestDto requestBody) {
        String authToken = authService.loginOAuthGoogle(requestBody);

        JSONObject tokenResponse;
        try {
            tokenResponse = new JSONObject("{\"token\": \"" + authToken + "\"}");
        } catch (JSONException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("json problem");
        }
        return ResponseEntity.ok(tokenResponse.toString());
    }
}
