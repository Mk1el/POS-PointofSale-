package com.Moses.Moses.POS.System.controller;

import com.Moses.Moses.POS.System.exception.UserException;
import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.modal.storeModal.StoreStatus;
import com.Moses.Moses.POS.System.payload.dto.StoreDto.StoreDTO;
import com.Moses.Moses.POS.System.service.UserService;
import com.Moses.Moses.POS.System.service.store.StoreService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN','STORE_MANAGER')")
    public ResponseEntity<StoreDTO> createStore(
            @RequestBody StoreDTO storeDTO) throws UserException {

        User currentUser = userService.getCurrentUser();

        StoreDTO createdStore = storeService.createStore(storeDTO, currentUser);

        return new ResponseEntity<>(createdStore, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StoreDTO> getStoreById(
            @PathVariable Long id) throws Exception {

        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STORE_MANAGER')")
    public ResponseEntity<List<StoreDTO>> getAllStores() {

        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN','STORE_MANAGER')")
    public ResponseEntity<StoreDTO> getStoreByAdmin() throws Exception {

        return ResponseEntity.ok(
                com.Moses.Moses.POS.System.mapper.StoreMapper.toDTO(
                        storeService.getStoreByAdmin()
                )
        );
    }

    @GetMapping("/employee")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StoreDTO> getStoreByEmployee() throws Exception {

        return ResponseEntity.ok(storeService.getStoreByEmployee());
    }
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN','STORE_MANAGER')")
    public ResponseEntity<StoreDTO> updateStore(
            @RequestBody StoreDTO storeDTO) throws Exception {

        User currentUser = userService.getCurrentUser();

        return ResponseEntity.ok(
                storeService.updateStore(storeDTO, currentUser)
        );
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteStore(@PathVariable Long id) {

        storeService.deleteStore(id);

        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/moderate")
    public ResponseEntity<StoreDTO> moderateStore(@PathVariable Long id, @RequestParam StoreStatus status) throws Exception{
        return ResponseEntity.ok(storeService.moderateStore(id, status));
    }
}