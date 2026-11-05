package com.Moses.Moses.POS.System.controller;

import com.Moses.Moses.POS.System.exception.UserException;
import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.payload.dto.ProductDto.ProductDTO;
import com.Moses.Moses.POS.System.service.UserService;
import com.Moses.Moses.POS.System.service.product.ProductService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    // CREATE PRODUCT
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STORE_MANAGER')")
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody ProductDTO productDTO
    ) throws Exception {

        User currentUser = userService.getCurrentUser();

        ProductDTO createdProduct =
                productService.createProduct(
                        productDTO,
                        currentUser
                );

        return new ResponseEntity<>(
                createdProduct,
                HttpStatus.CREATED
        );
    }

    // UPDATE PRODUCT
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STORE_MANAGER')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) throws Exception {
        User currentUser = userService.getCurrentUser();

        ProductDTO updatedProduct =
                productService.updateProduct(
                        id,
                        productDTO,
                        currentUser
                );

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STORE_MANAGER')")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id
    ) throws Exception {

        User currentUser = userService.getCurrentUser();

        productService.deleteProduct(
                id,
                currentUser
        );

        return ResponseEntity.ok("Product deleted successfully");
    }


    // GET PRODUCTS BY STORE
    @GetMapping("/store/{storeId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ProductDTO>> getProductsByStoreId(@PathVariable Long storeId) {

        List<ProductDTO> products =
                productService.getProductsByStoreId(storeId);

        return ResponseEntity.ok(products);
    }

    // SEARCH PRODUCTS
    @GetMapping("/store/{storeId}/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ProductDTO>> searchProducts(
            @PathVariable Long storeId,
            @RequestParam String keyword
    ) {

        List<ProductDTO> products =
                productService.searchByKeyword(
                        storeId,
                        keyword
                );

        return ResponseEntity.ok(products);
    }
}