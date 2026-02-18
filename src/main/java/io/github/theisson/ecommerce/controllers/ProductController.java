package io.github.theisson.ecommerce.controllers;

import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.github.theisson.ecommerce.dto.ProductRequestDTO;
import io.github.theisson.ecommerce.dto.ProductResponseDTO;
import io.github.theisson.ecommerce.services.application.CreateProduct;
import io.github.theisson.ecommerce.services.application.GetProduct;
import io.github.theisson.ecommerce.services.application.ListProducts;
import io.github.theisson.ecommerce.services.application.UpdateProduct;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ListProducts listProducts;
    private final GetProduct getProduct;
    private final CreateProduct createProduct;
    private final UpdateProduct updateProduct;

    public ProductController(ListProducts listProducts, GetProduct getProduct, CreateProduct createProduct, UpdateProduct updateProduct) {
        this.listProducts = listProducts;
        this.getProduct = getProduct;
        this.createProduct = createProduct;
        this.updateProduct = updateProduct;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAll(Pageable pageable) {
        Page<ProductResponseDTO> list = listProducts.execute(pageable);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        ProductResponseDTO product = getProduct.execute(id);

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> insert(@Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO newProduct = createProduct.execute(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newProduct.id())
                .toUri();

        return ResponseEntity.created(uri).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO updatedProduct = updateProduct.execute(id, dto);

        return ResponseEntity.ok(updatedProduct);
    }
}
