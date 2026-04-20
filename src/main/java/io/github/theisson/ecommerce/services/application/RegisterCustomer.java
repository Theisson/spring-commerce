package io.github.theisson.ecommerce.services.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.theisson.ecommerce.dto.CustomerRequestDTO;
import io.github.theisson.ecommerce.dto.CustomerResponseDTO;
import io.github.theisson.ecommerce.exceptions.DatabaseException;
import io.github.theisson.ecommerce.models.entities.Customer;
import io.github.theisson.ecommerce.models.entities.User;
import io.github.theisson.ecommerce.models.types.*;
import io.github.theisson.ecommerce.repositories.CustomerRepository;
import io.github.theisson.ecommerce.repositories.UserRepository;

@Service
public class RegisterCustomer {
    
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String pepper;

    public RegisterCustomer(
        CustomerRepository customerRepository, 
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        @Value("${app.security.password.pepper}") String pepper
    ) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.pepper = pepper;
    }

    @Transactional
    public CustomerResponseDTO execute(CustomerRequestDTO dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new DatabaseException("E-mail já cadastrado.");
        }

        if (userRepository.findByUsername(dto.username()).isPresent()) {
            throw new DatabaseException("Nome de usuário já cadastrado.");
        }

        if (customerRepository.findByCpf(dto.cpf()).isPresent()) {
            throw new DatabaseException("CPF já cadastrado.");
        }

        String pepperedPassword = dto.password() + pepper;
        String hash = passwordEncoder.encode(pepperedPassword);

        User user = new User(
            new Username(dto.username()),
            new Email(dto.email()),
            hash,
            UserRole.CUSTOMER
        );

        Customer customer = new Customer(
            user,
            new Cpf(dto.cpf()),
            new PhoneNumber(dto.phoneNumber()),
            dto.birthDate()
        );

        customer = customerRepository.save(customer);

        return new CustomerResponseDTO(customer);
    }
}
