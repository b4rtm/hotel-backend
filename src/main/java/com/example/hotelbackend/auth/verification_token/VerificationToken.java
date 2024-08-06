package com.example.hotelbackend.auth.verification_token;

import com.example.hotelbackend.customer.Customer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "verification_tokens")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime expiresAt;

    @ManyToOne
    private Customer customer;

    public VerificationToken(String token, LocalDateTime expiresAt, Customer customer) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.customer = customer;
    }

}
