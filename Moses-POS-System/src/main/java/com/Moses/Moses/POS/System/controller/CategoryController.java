package com.Moses.Moses.POS.System.controller;

import com.Moses.Moses.POS.System.payload.dto.categoryDto.CategoryDTO;
import com.Moses.Moses.POS.System.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // CREATE CATEGORY
    @PostMapping
    @PreAuthorize("hasAnyRole('STORE_ADMIN', 'STORE_MANAGER')")
    public ResponseEntity<CategoryDTO> createCategory(
            @RequestBody CategoryDTO categoryDTO
    ) throws Exception {

        CategoryDTO createdCategory =
                categoryService.createCategory(categoryDTO);

        return new ResponseEntity<>(
                createdCategory,
                HttpStatus.CREATED
        );
    }

    // GET CATEGORIES BY STORE
    @GetMapping("/store/{storeId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByStore(
            @PathVariable Long storeId
    ) {

        List<CategoryDTO> categories =
                categoryService.getCategoriesByStore(storeId);

        return ResponseEntity.ok(categories);
    }

    // UPDATE CATEGORY
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('STORE_ADMIN', 'STORE_MANAGER')")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO
    ) throws Exception {

        CategoryDTO updatedCategory =
                categoryService.updateCategory(
                        id,
                        categoryDTO
                );

        return ResponseEntity.ok(updatedCategory);
    }

    // DELETE CATEGORY
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('STORE_ADMIN', 'STORE_MANAGER')")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long id
    ) throws Exception {

        categoryService.deleteCategory(id);

        return ResponseEntity.ok(
                "Category deleted successfully"
        );
    }
}