package com.Moses.Moses.POS.System.payload.dto.ProductDto;

import com.Moses.Moses.POS.System.modal.categoryModal.Category;
import com.Moses.Moses.POS.System.modal.storeModal.Store;
import com.Moses.Moses.POS.System.payload.dto.categoryDto.CategoryDTO;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String sku;
    private String description;
    private String brand;
    private Double mrp;
    private Double sellingPrice;
    private String image;
    private CategoryDTO category;
    private Long categoryId;
    private Long storeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
