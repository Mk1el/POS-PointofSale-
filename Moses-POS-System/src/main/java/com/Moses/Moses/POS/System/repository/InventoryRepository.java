package com.Moses.Moses.POS.System.repository;

import com.Moses.Moses.POS.System.modal.inventoryModal.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
//    Inventory findProductIdAndBranchId(Long productId, Long branchId);
    Optional<Inventory> findByProduct_IdAndBranch_Id(Long productId, Long branchId);

    List<Inventory> findByBranchId(Long branchId);
}
