package com.Moses.Moses.POS.System.repository;

import com.Moses.Moses.POS.System.modal.storeModal.Store;
import com.Moses.Moses.POS.System.modal.storeModal.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface StoreDashboardRepository extends JpaRepository<Store, Long> {
    long count();
    long countByStatus(StoreStatus status);
    @Query("""
            SELECT COUNT(s)
            FROM Store s
            WHERE s.createdAt >= : startDate
            AND s.createdAt < :endDate
            """)
    long countRegistrationsBetween(
            @Param("startDate")LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
            );
}
