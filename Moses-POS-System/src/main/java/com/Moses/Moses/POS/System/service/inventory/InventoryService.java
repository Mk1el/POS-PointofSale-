package com.Moses.Moses.POS.System.service.inventory;

import com.Moses.Moses.POS.System.payload.dto.inventoryDto.InventoryDTO;

import java.util.List;

public interface InventoryService {
    InventoryDTO createInventory(InventoryDTO inventoryDTO) throws Exception;
    InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) throws Exception;
    void deleteInventory(Long id) throws Exception;
    InventoryDTO getInventoryById(Long id) throws Exception;
    InventoryDTO getInventoryByProductAndBranchId(Long productId, Long branchId);
    List<InventoryDTO> getAllInventoryByBranchId(Long branchId);
}
