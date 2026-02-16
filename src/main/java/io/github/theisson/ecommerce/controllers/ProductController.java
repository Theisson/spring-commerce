package io.github.theisson.ecommerce.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.github.theisson.ecommerce.dto.ProductDTO;
import io.github.theisson.ecommerce.services.application.GetProduct;
import io.github.theisson.ecommerce.services.application.ListProducts;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ListProducts listProducts;
    private final GetProduct getProduct;

    public ProductController(ListProducts listProducts, GetProduct getProduct) {
        this.listProducts = listProducts;
        this.getProduct = getProduct;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
        Page<ProductDTO> list = listProducts.execute(pageable);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO product = getProduct.execute(id);

        return ResponseEntity.ok(product);
    }
}
