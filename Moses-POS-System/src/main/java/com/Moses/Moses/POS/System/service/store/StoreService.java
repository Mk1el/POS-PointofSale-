package com.Moses.Moses.POS.System.service.store;

import com.Moses.Moses.POS.System.exception.UserException;
import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.modal.storeModal.Store;
import com.Moses.Moses.POS.System.modal.storeModal.StoreStatus;
import com.Moses.Moses.POS.System.payload.dto.StoreDto.StoreDTO;

import java.util.List;

public interface StoreService {
    StoreDTO createStore(StoreDTO storeDTO, User user);
    StoreDTO getStoreById(Long id) throws Exception;
    List<StoreDTO> getAllStores();
    Store getStoreByAdmin() throws UserException;
    StoreDTO moderateStore(Long id, StoreStatus status) throws Exception;
    StoreDTO updateStore(StoreDTO storeDTO, User user) throws Exception;
    void deleteStore(Long id);
    StoreDTO getStoreByEmployee() throws UserException;
}
