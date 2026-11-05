package com.Moses.Moses.POS.System.service.inventory;

import com.Moses.Moses.POS.System.mapper.InventoryMapper;
import com.Moses.Moses.POS.System.modal.branchModal.Branch;
import com.Moses.Moses.POS.System.modal.inventoryModal.Inventory;
import com.Moses.Moses.POS.System.modal.productModal.Product;
import com.Moses.Moses.POS.System.payload.dto.inventoryDto.InventoryDTO;
import com.Moses.Moses.POS.System.repository.BranchRepository;
import com.Moses.Moses.POS.System.repository.InventoryRepository;
import com.Moses.Moses.POS.System.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{
    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    @Override
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) throws Exception {
        Branch branch = branchRepository.findById(inventoryDTO.getBranchId()).orElseThrow(
                ()-> new Exception("Branch does not exist!")
        );
        Product product = productRepository.findById(inventoryDTO.getProductId()).orElseThrow(
                ()-> new Exception("Product does not exist!")
        );
        Inventory inventory = InventoryMapper.toEntity(inventoryDTO, branch, product);
        Inventory savedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toDTO(savedInventory);
    }

    @Override
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) throws Exception {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Inventory not found!")
        );
        inventory.setQuantity(inventoryDTO.getQuantity());
        Inventory updatedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toDTO(updatedInventory);
    }

    @Override
    public void deleteInventory(Long id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Inventory not found!")
        );
        inventoryRepository.delete(inventory);

    }

    @Override
    public InventoryDTO getInventoryById(Long id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Inventory not found!")
        );
        return InventoryMapper.toDTO(inventory);
    }

    @Override
    public InventoryDTO getInventoryByProductAndBranchId(
            Long productId,
            Long branchId
    ) {

        Inventory inventory = inventoryRepository
                .findByProduct_IdAndBranch_Id(productId, branchId)
                .orElseThrow(() -> new RuntimeException(
                        "Inventory not found for product ID: "
                                + productId
                                + " and branch ID: "
                                + branchId
                ));

        return InventoryMapper.toDTO(inventory);
    }

    @Override
    public List<InventoryDTO> getAllInventoryByBranchId(Long branchId) {
        List<Inventory> inventories = inventoryRepository.findByBranchId(branchId);
        return inventories.stream().map(
                InventoryMapper::toDTO
        ).collect(Collectors.toList());
    }
}
