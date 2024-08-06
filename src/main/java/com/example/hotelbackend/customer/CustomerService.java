package com.example.hotelbackend.customer;

import com.example.hotelbackend.auth.verification_token.VerificationToken;
import com.example.hotelbackend.auth.verification_token.VerificationTokenRepository;
import com.example.hotelbackend.smtp.ClientSMTP;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final VerificationTokenRepository tokenRepository;
    private final ClientSMTP clientSMTP;
    private final CustomerDtoMapper customerDtoMapper;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, VerificationTokenRepository tokenRepository, ClientSMTP clientSMTP, CustomerDtoMapper customerDtoMapper, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.tokenRepository = tokenRepository;
        this.clientSMTP = clientSMTP;
        this.customerDtoMapper = customerDtoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerDto saveCustomer(CustomerDto dto){
        Customer customer = customerDtoMapper.map(dto);
        String passwordHash = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(passwordHash);
        customer.setRole(Customer.Role.ROLE_USER);
        Customer savedCustomer = customerRepository.save(customer);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, LocalDateTime.now().plusHours(24), savedCustomer);
        tokenRepository.save(verificationToken);
        sendVerificationEmail("bartek.matusiak12@gmail.com", token);


        return customerDtoMapper.map(savedCustomer);
    }

    public Customer createGoogleCustomerIfNotExist(Customer customer){
        Customer customer1 = customerRepository.findByEmail(customer.getEmail()).orElse(null);
        if(customer1 == null){
            customer.setRole(Customer.Role.ROLE_USER);
            customer.setEnabled(true);
            return customerRepository.save(customer);
        }
        return customer1;
    }

    public void sendVerificationEmail(String email, String token) {
        String url = "http://localhost:8080/auth/confirm?token=" + token;
        String message = "Aby potwierdzić rejestrację, kliknij w poniższy link:\n" + url;
        clientSMTP.sendEmail(email, "Potwierdzenie rejestracji", message);
    }

    public String confirmToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token nie znaleziony"));

        if (verificationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token wygasł");
        }

        Customer customer = verificationToken.getCustomer();
        customer.setEnabled(true);
        customerRepository.save(customer);
        return "Konto aktywowane";
    }

    List<CustomerDto> getUsers(){
        return customerRepository.findAll().stream().map(customerDtoMapper::map).toList();
    }

    Optional<CustomerDto> getCustomerByEmail(String email){
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setPassword("");
            return Optional.of(customerDtoMapper.map(customer));
        }
        return Optional.empty();    }

    public Optional<Customer> getCustomerById(Long id){
        return customerRepository.findById(id);
    }

    CustomerDto replaceCustomer(Long customerId, CustomerDto customerDto){
        Customer existingCustomer = customerRepository.findById(customerId).get();
        Customer customer = customerDtoMapper.map(customerDto);
        customer.setPassword(existingCustomer.getPassword());
        customer.setRole(existingCustomer.getRole());
        customer.setId(customerId);
        Customer updatedCustomer = customerRepository.save(customer);
        return customerDtoMapper.map(updatedCustomer);
    }

    void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }
}
