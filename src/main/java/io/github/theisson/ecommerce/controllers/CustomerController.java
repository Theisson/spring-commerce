package io.github.theisson.ecommerce.controllers;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.github.theisson.ecommerce.dto.CustomerRequestDTO;
import io.github.theisson.ecommerce.dto.CustomerResponseDTO;
import io.github.theisson.ecommerce.services.application.RegisterCustomer;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    
    private final RegisterCustomer registerCustomer;

    public CustomerController(RegisterCustomer registerCustomer) {
        this.registerCustomer = registerCustomer;
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> register(@Valid @RequestBody CustomerRequestDTO dto) {
        CustomerResponseDTO newCustomer = registerCustomer.execute(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newCustomer.id())
            .toUri();

        return ResponseEntity.created(uri).body(newCustomer);
    }
}
