package com.Moses.Moses.POS.System.repository;

import com.Moses.Moses.POS.System.modal.storeModal.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
//    Store findByStoreAdminId(Long id);
//    Store findById(Long id);
    Optional<Store> findById(Long id);
    Store findByStoreAdminId(Long id);
}
