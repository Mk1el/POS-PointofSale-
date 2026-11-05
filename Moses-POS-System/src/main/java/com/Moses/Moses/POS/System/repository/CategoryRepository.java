package com.Moses.Moses.POS.System.repository;

import com.Moses.Moses.POS.System.modal.categoryModal.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByStoreId(long storeId);
}
