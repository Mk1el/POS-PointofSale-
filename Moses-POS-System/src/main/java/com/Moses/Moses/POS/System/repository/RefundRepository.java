package com.Moses.Moses.POS.System.repository;

import com.Moses.Moses.POS.System.modal.refundModal.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, Long> {
    List<Refund> findByCashierAndCreatedAtBetween(Long cashier, LocalDateTime from, LocalDateTime to);
    List<Refund> findByCashierId(Long id);
    List<Refund> findByShiftReportId(Long id);
    List<Refund> findByBranchId(Long id);

}
