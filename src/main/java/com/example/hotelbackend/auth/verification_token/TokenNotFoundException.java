package com.example.hotelbackend.auth.verification_token;

public class TokenNotFoundException extends RuntimeException{
    public TokenNotFoundException(String message) {
        super(message);
    }
}
