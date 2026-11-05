package com.Moses.Moses.POS.System.mapper;

import com.Moses.Moses.POS.System.modal.branchModal.Branch;
import com.Moses.Moses.POS.System.modal.inventoryModal.Inventory;
import com.Moses.Moses.POS.System.modal.productModal.Product;
import com.Moses.Moses.POS.System.payload.dto.inventoryDto.InventoryDTO;

public class InventoryMapper {
    public static InventoryDTO toDTO(Inventory inventory){
        return InventoryDTO.builder()
                .id(inventory.getId())
                .branchId(inventory.getBranch().getId())
                .productId(inventory.getProduct().getId())
                .product(ProductMapper.toDTO(inventory.getProduct()))
                .quantity(inventory.getQuantity())
                .build();

    }
    public static Inventory toEntity(InventoryDTO inventoryDTO, Branch branch, Product product){
        return Inventory.builder()
                .branch(branch)
                .product(product)
                .quantity(inventoryDTO.getQuantity())
                .build();
    }
}
