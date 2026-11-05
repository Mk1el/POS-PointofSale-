package com.Moses.Moses.POS.System.mapper;

import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.modal.storeModal.Store;
import com.Moses.Moses.POS.System.payload.dto.StoreDto.StoreDTO;

public class StoreMapper {
    public static StoreDTO toDTO(Store store){
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(store.getId());
        storeDTO.setBrand(store.getBrand());
        storeDTO.setDescription(store.getDescription());
        storeDTO.setStoreAdmin(UserMapper.toDTO(store.getStoreAdmin()));
        storeDTO.setStoreType(store.getStoreType());
        storeDTO.setStoreType(store.getStoreType());
        storeDTO.setContact(store.getContact());
        store.setCreatedAt(store.getCreatedAt());
        store.setUpdatedAt(store.getUpdatedAt());
        store.setStatus(store.getStatus());
        return storeDTO;
    }
    public static Store toEntity(StoreDTO storeDTO, User storeAdmin){
        Store store = new Store();
        store.setId(storeDTO.getId());
        store.setBrand(storeDTO.getBrand());
        store.setDescription(storeDTO.getDescription());
        store.setStoreAdmin(storeAdmin);
        store.setStoreType(storeDTO.getStoreType());
        store.setContact(storeDTO.getContact());
        store.setCreatedAt(storeDTO.getCreatedAt());
        store.setUpdatedAt(storeDTO.getUpdatedAt());
        return store;    }
}
