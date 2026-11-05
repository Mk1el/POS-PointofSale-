
package com.Moses.Moses.POS.System.repository;

import com.Moses.Moses.POS.System.modal.orderModal.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BranchAnalyticsRepository extends JpaRepository<Order, Long> {

    @Query("""
        SELECT COUNT(o)
        FROM Order o
        WHERE o.branch.id = :branchId
          AND o.createdAt >= :start
          AND o.createdAt < :end
    """)
    Long countOrders(
            @Param("branchId") Long branchId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        SELECT COALESCE(SUM(o.totalAmount), 0)
        FROM Order o
        WHERE o.branch.id = :branchId
          AND o.createdAt >= :start
          AND o.createdAt < :end
    """)
    Double sumSales(
            @Param("branchId") Long branchId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        SELECT COUNT(DISTINCT o.customer.id)
        FROM Order o
        WHERE o.branch.id = :branchId
          AND o.customer IS NOT NULL
          AND o.createdAt >= :start
          AND o.createdAt < :end
    """)
    Long countUniqueCustomers(
            @Param("branchId") Long branchId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        SELECT COALESCE(SUM(i.quantity), 0)
        FROM OrderItem i
        WHERE i.order.branch.id = :branchId
          AND i.order.createdAt >= :start
          AND i.order.createdAt < :end
    """)
    Long sumItemsSold(
            @Param("branchId") Long branchId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        SELECT FUNCTION('DATE', o.createdAt),
               COUNT(o),
               SUM(o.totalAmount)
        FROM Order o
        WHERE o.branch.id = :branchId
          AND o.createdAt >= :start
          AND o.createdAt < :end
        GROUP BY FUNCTION('DATE', o.createdAt)
        ORDER BY FUNCTION('DATE', o.createdAt)
    """)
    List<Object[]> findDailySales(
            @Param("branchId") Long branchId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        SELECT p.id,
               p.name,
               SUM(i.quantity),
               SUM(i.quantity * i.price),
               0.0
        FROM OrderItem i
        JOIN i.product p
        JOIN i.order o
        WHERE o.branch.id = :branchId
          AND o.createdAt >= :start
          AND o.createdAt < :end
        GROUP BY p.id, p.name
        ORDER BY SUM(i.quantity) DESC
    """)
    List<Object[]> findTopProducts(
            @Param("branchId") Long branchId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        SELECT u.id,
               u.fullName,
               COUNT(o),
               SUM(o.totalAmount)
        FROM Order o
        JOIN o.cashier u
        WHERE o.branch.id = :branchId
          AND o.createdAt >= :start
          AND o.createdAt < :end
        GROUP BY u.id, u.fullName
        ORDER BY SUM(o.totalAmount) DESC
    """)
    List<Object[]> findTopCashiers(
            @Param("branchId") Long branchId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        SELECT c.id,
               c.name,
               SUM(i.quantity),
               SUM(i.quantity * i.price)
        FROM OrderItem i
        JOIN i.product p
        JOIN p.category c
        JOIN i.order o
        WHERE o.branch.id = :branchId
          AND o.createdAt >= :start
          AND o.createdAt < :end
        GROUP BY c.id, c.name
        ORDER BY SUM(i.quantity * i.price) DESC
    """)
    List<Object[]> findCategorySales(
            @Param("branchId") Long branchId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        SELECT o.paymentType,
               COUNT(o),
               SUM(o.totalAmount)
        FROM Order o
        WHERE o.branch.id = :branchId
          AND o.createdAt >= :start
          AND o.createdAt < :end
        GROUP BY o.paymentType
    """)
    List<Object[]> findPaymentBreakdown(
            @Param("branchId") Long branchId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}