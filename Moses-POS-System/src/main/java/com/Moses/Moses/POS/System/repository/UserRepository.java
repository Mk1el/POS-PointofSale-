package com.Moses.Moses.POS.System.repository;

import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.modal.storeModal.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByStore(Store store);
    List<User> findByBranchId(Long branchId);

}
