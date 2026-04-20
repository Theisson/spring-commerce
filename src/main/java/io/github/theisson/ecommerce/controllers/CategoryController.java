package io.github.theisson.ecommerce.controllers;

import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.github.theisson.ecommerce.dto.CategoryRequestDTO;
import io.github.theisson.ecommerce.dto.CategoryResponseDTO;
import io.github.theisson.ecommerce.services.application.CreateCategory;
import io.github.theisson.ecommerce.services.application.DeleteCategory;
import io.github.theisson.ecommerce.services.application.GetCategory;
import io.github.theisson.ecommerce.services.application.ListCategories;
import io.github.theisson.ecommerce.services.application.UpdateCategory;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final ListCategories listCategories;
    private final GetCategory getCategory;
    private final CreateCategory createCategory;
    private final UpdateCategory updateCategory;
    private final DeleteCategory deleteCategory;

    public CategoryController(
        ListCategories listCategories,
        GetCategory getCategory,
        CreateCategory createCategory,
        UpdateCategory updateCategory,
        DeleteCategory deleteCategory
    ) {
        this.listCategories = listCategories;
        this.getCategory = getCategory;
        this.createCategory = createCategory;
        this.updateCategory = updateCategory;
        this.deleteCategory = deleteCategory;
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> findAll(Pageable pageable) {
        Page<CategoryResponseDTO> list = listCategories.execute(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id) {
        CategoryResponseDTO category = getCategory.execute(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> insert(@Valid @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO newCategory = createCategory.execute(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCategory.id())
                .toUri();

        return ResponseEntity.created(uri).body(newCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO updatedCategory = updateCategory.execute(id, dto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteCategory.execute(id);
        return ResponseEntity.noContent().build();
    }
}
