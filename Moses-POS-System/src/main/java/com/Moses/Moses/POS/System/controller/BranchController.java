package com.Moses.Moses.POS.System.controller;
import com.Moses.Moses.POS.System.payload.dto.branchDto.BranchDTO;
import com.Moses.Moses.POS.System.service.branch.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;
    @PostMapping
    @PreAuthorize("hasAnyRole('STORE_ADMIN', 'STORE_MANAGER')")
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) throws Exception {
        BranchDTO createdBranch = branchService.createBranch(branchDTO, null);

        return ResponseEntity.ok(createdBranch);
    }

    @GetMapping("/store/{storeId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<BranchDTO>> getBranchesByStoreId(@PathVariable Long storeId) {

        List<BranchDTO> createdBranches = branchService.getAllBranchesByStoreId(storeId);

        return ResponseEntity.ok(createdBranches);
    }
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable Long id) throws Exception {

        BranchDTO createdBranch = branchService.getBranchById(id);

        return ResponseEntity.ok(createdBranch);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('STORE_ADMIN', 'STORE_MANAGER')")
    public ResponseEntity<BranchDTO> updateBranch(
            @PathVariable Long id,
            @RequestBody BranchDTO branchDTO
    ) throws Exception {

        BranchDTO updatedBranch =
                branchService.updateBranch(id, branchDTO, null);

        return ResponseEntity.ok(updatedBranch);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('STORE_ADMIN', 'STORE_MANAGER')")
    public ResponseEntity<String> deleteBranch(
            @PathVariable Long id
    ) throws Exception {
        branchService.deleteBranch(id);
        return ResponseEntity.ok(
                "Branch deleted successfully"
        );
    }
}

