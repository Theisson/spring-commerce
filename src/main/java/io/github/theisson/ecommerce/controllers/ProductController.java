package io.github.theisson.ecommerce.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.theisson.ecommerce.dto.ProductDTO;
import io.github.theisson.ecommerce.services.application.ListProducts;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ListProducts listProducts;

    public ProductController(ListProducts listProducts) {
        this.listProducts = listProducts;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
        Page<ProductDTO> list = listProducts.execute(pageable);

        return ResponseEntity.ok(list);
    }
}
