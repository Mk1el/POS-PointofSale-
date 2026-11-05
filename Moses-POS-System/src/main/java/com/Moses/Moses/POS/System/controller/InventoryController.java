package com.Moses.Moses.POS.System.controller;

import com.Moses.Moses.POS.System.payload.dto.inventoryDto.InventoryDTO;
import com.Moses.Moses.POS.System.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping()
    public ResponseEntity<InventoryDTO> create(
            @RequestBody InventoryDTO inventoryDTO
    ) throws Exception {

        return ResponseEntity.ok(inventoryService.createInventory(inventoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> update(
            @PathVariable Long id,
            @RequestBody InventoryDTO inventoryDTO
    ) throws Exception {

        InventoryDTO updatedInventory = inventoryService.updateInventory(id, inventoryDTO);

        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/removeinventory/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) throws Exception {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/inventorybybranch/{id}")
    public ResponseEntity<InventoryDTO> getInventoryByBranch(
            @RequestBody InventoryDTO inventoryDTO
    ) throws Exception {
        return ResponseEntity.ok(inventoryService.createInventory(inventoryDTO));
    }
    @GetMapping("/branch/{branchId}/product/{productId}")
    public ResponseEntity<InventoryDTO> getInventoryByProductAndBranchId(
            @PathVariable Long branchId,
            @PathVariable Long productId
    ) {

        return ResponseEntity.ok(inventoryService.getInventoryByProductAndBranchId(productId, branchId));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<InventoryDTO>> getAllInventoryByBranch(

            @PathVariable Long branchId
    ) {

        return ResponseEntity.ok(inventoryService.getAllInventoryByBranchId(branchId));
    }
}