package io.github.theisson.ecommerce.services.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.theisson.ecommerce.dto.ProductDTO;
import io.github.theisson.ecommerce.models.entities.Product;
import io.github.theisson.ecommerce.services.domain.ProductService;

@Service
public class ProductCatalogService {
    private final ProductService productService;

    public ProductCatalogService(ProductService productService) {
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> getCatalog(Pageable pageable) {
        Page<Product> result = productService.findAll(pageable);

        return result.map(ProductDTO::new);
    }
}
