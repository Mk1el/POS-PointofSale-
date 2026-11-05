package com.Moses.Moses.POS.System.service.store;

import com.Moses.Moses.POS.System.exception.UserException;
import com.Moses.Moses.POS.System.mapper.StoreMapper;
import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.modal.storeModal.Store;
import com.Moses.Moses.POS.System.modal.storeModal.StoreContact;
import com.Moses.Moses.POS.System.modal.storeModal.StoreStatus;
import com.Moses.Moses.POS.System.payload.dto.StoreDto.StoreDTO;
import com.Moses.Moses.POS.System.repository.StoreRepository;
import com.Moses.Moses.POS.System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public StoreDTO createStore(StoreDTO storeDTO, User user) {
        // Convert DTO to entity and persist
        Store store = StoreMapper.toEntity(storeDTO, user);
        return StoreMapper.toDTO(storeRepository.save(store));
    }

    @Override
    public StoreDTO getStoreById(Long id) throws Exception {
        // Retrieve store by ID or throw exception if not found
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new Exception("Store not found!"));

        return StoreMapper.toDTO(store);
    }

    @Override
    public List<StoreDTO> getAllStores() {
        // Fetch all stores and convert to DTO list
        List<Store> stores = storeRepository.findAll();

        return stores.stream()
                .map(StoreMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public StoreDTO moderateStore(Long id, StoreStatus status) throws Exception{
        Store store = storeRepository.findById(id).orElseThrow(()-> new Exception("Store not found"));
        store.setStatus(status);
        Store updatedStore = storeRepository.save(store);
        return StoreMapper.toDTO(updatedStore);
    }

    @Override
    public Store getStoreByAdmin() throws UserException {
        // Get store associated with the currently logged-in admin
        User admin = userService.getCurrentUser();

//        Store store =
        Store store = storeRepository.findByStoreAdminId(admin.getId());

        if (store == null) {
            throw new UserException("Store not found for this admin!");
        }

        return store;
    }

    @Override
    public StoreDTO updateStore(StoreDTO storeDTO, User user) throws Exception {

        // Get current authenticated user
        User currentUser = userService.getCurrentUser();

        // Find store belonging to current admin
        Store existing = storeRepository.findByStoreAdminId(currentUser.getId());

        if (existing == null) {
            throw new Exception("Store not found!");
        }

        // Update basic store information
        if (storeDTO.getBrand() != null) {
            existing.setBrand(storeDTO.getBrand());
        }

        if (storeDTO.getDescription() != null) {
            existing.setDescription(storeDTO.getDescription());
        }

        if (storeDTO.getStoreType() != null) {
            existing.setStoreType(storeDTO.getStoreType());
        }

        // Update contact information
        if (storeDTO.getContact() != null) {

            StoreContact contact = StoreContact.builder()
                    .address(storeDTO.getContact().getAddress())
                    .phone(storeDTO.getContact().getPhone())
                    .email(storeDTO.getContact().getEmail())
                    .build();

            existing.setContact(contact);
        }

        // Save updated store
        Store updatedStore = storeRepository.save(existing);

        return StoreMapper.toDTO(updatedStore);
    }

    @Override
    public void deleteStore(Long id) {

        // Find store or throw exception if not found
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found!"));

        // Delete store
        storeRepository.delete(store);
    }

    @Override
    public StoreDTO getStoreByEmployee() throws UserException {

        // Get current logged-in employee
        User currentUser = userService.getCurrentUser();

        if (currentUser == null || currentUser.getStore() == null) {
            throw new UserException("You do not have permission to access the store!");
        }

        return StoreMapper.toDTO(currentUser.getStore());
    }
}