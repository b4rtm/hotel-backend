package com.example.hotelbackend.customer;

public class ExpiredTokenException extends RuntimeException{
    public ExpiredTokenException(String message) {
        super(message);
    }
}
