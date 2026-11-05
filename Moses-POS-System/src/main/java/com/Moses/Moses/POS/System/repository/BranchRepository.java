package com.Moses.Moses.POS.System.repository;

import com.Moses.Moses.POS.System.modal.branchModal.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findByStoreId(Long storeId);
}
