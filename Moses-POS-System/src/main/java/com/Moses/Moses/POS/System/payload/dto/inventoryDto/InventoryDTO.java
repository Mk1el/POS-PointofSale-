package com.Moses.Moses.POS.System.payload.dto.inventoryDto;

import com.Moses.Moses.POS.System.modal.branchModal.Branch;
import com.Moses.Moses.POS.System.modal.productModal.Product;
import com.Moses.Moses.POS.System.payload.dto.ProductDto.ProductDTO;
import com.Moses.Moses.POS.System.payload.dto.branchDto.BranchDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryDTO {
    private Long id;
    private BranchDTO branch;
    private Long branchId;
    private Long productId;
    private ProductDTO product;
    private Integer quantity;
    private LocalDateTime lastUpdate;

}
