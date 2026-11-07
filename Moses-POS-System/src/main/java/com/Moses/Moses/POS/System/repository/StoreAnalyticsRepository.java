package com.Moses.Moses.POS.System.repository;


import com.Moses.Moses.POS.System.modal.orderModal.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StoreAnalyticsRepository extends JpaRepository<Order, Long> {

    // --- Projections ---

    interface KpiProjection {
        Long getTotalOrders();
        BigDecimal getTotalRevenue();
        Long getTotalCustomers();
    }

    interface SalesTrendProjection {
        String getPeriodLabel();
        BigDecimal getRevenue();
        Long getOrderCount();
    }

    interface MonthlySalesProjection {
        String getMonth();
        BigDecimal getRevenue();
        Long getOrders();
    }

    interface DailySalesProjection {
        LocalDate getSaleDate();
        Long getTotalOrders();
        BigDecimal getRevenue();
    }

    interface CategorySalesProjection {
        Long getCategoryId();
        String getCategoryName();
        Long getQuantitySold();
        BigDecimal getTotalSales();
    }

    interface PaymentMethodProjection {
        String getPaymentMethod();
        BigDecimal getTotalAmount();
        Long getCount();
    }

    interface BranchSalesProjection {
        Long getBranchId();
        String getBranchName();
        BigDecimal getTotalSales();
        Long getOrderCount();
    }

    interface PaymentBreakdownProjection {
        String getPaymentMethod();
        Long getTotalTransactions();
        BigDecimal getTotalAmount();
        Long getSuccessfulTransactions();
        Long getFailedTransactions();
    }

    interface AlertProjection {
        String getId();
        String getType();
        String getSeverity();
        String getMessage();
        Long getBranchId();
        LocalDateTime getTimestamp();
    }

    // --- DB Queries ---

    // 1. Overview KPIs
    @Query(value = """
        SELECT 
            COUNT(o.id) AS totalOrders,
            COALESCE(SUM(o.total_amount), 0) AS totalRevenue,
            COUNT(DISTINCT o.customer_id) AS totalCustomers
        FROM orders o
        JOIN branches b ON o.branch_id = b.id
        WHERE b.store_admin_id = :storeAdminId AND o.status = 'COMPLETED'
        """, nativeQuery = true)
    KpiProjection findKpiSummary(@Param("storeAdminId") Long storeAdminId);

    // 2. Sales Trends Daily
    @Query(value = """
        SELECT 
            CAST(DATE(o.created_at) AS CHAR) AS periodLabel,
            COALESCE(SUM(o.total_amount), 0) AS revenue,
            COUNT(o.id) AS orderCount
        FROM orders o
        JOIN branches b ON o.branch_id = b.id
        WHERE b.store_admin_id = :storeAdminId AND o.status = 'COMPLETED'
        GROUP BY DATE(o.created_at)
        ORDER BY DATE(o.created_at) ASC
        """, nativeQuery = true)
    List<SalesTrendProjection> findDailySalesTrends(@Param("storeAdminId") Long storeAdminId);

    // 2b. Sales Trends Weekly
    @Query(value = """
        SELECT 
            CONCAT(YEAR(o.created_at), '-W', LPAD(WEEK(o.created_at), 2, '0')) AS periodLabel,
            COALESCE(SUM(o.total_amount), 0) AS revenue,
            COUNT(o.id) AS orderCount
        FROM orders o
        JOIN branches b ON o.branch_id = b.id
        WHERE b.store_admin_id = :storeAdminId AND o.status = 'COMPLETED'
        GROUP BY YEAR(o.created_at), WEEK(o.created_at)
        ORDER BY YEAR(o.created_at) ASC, WEEK(o.created_at) ASC
        """, nativeQuery = true)
    List<SalesTrendProjection> findWeeklySalesTrends(@Param("storeAdminId") Long storeAdminId);

    // 3. Monthly Sales
    @Query(value = """
        SELECT 
            DATE_FORMAT(o.created_at, '%Y-%m') AS month,
            COALESCE(SUM(o.total_amount), 0) AS revenue,
            COUNT(o.id) AS orders
        FROM orders o
        JOIN branches b ON o.branch_id = b.id
        WHERE b.store_admin_id = :storeAdminId AND o.status = 'COMPLETED'
        GROUP BY DATE_FORMAT(o.created_at, '%Y-%m')
        ORDER BY month ASC
        """, nativeQuery = true)
    List<MonthlySalesProjection> findMonthlySales(@Param("storeAdminId") Long storeAdminId);

    // 4. Daily Sales
    @Query(value = """
        SELECT 
            DATE(o.created_at) AS saleDate,
            COUNT(o.id) AS totalOrders,
            COALESCE(SUM(o.total_amount), 0) AS revenue
        FROM orders o
        JOIN branches b ON o.branch_id = b.id
        WHERE b.store_admin_id = :storeAdminId AND o.status = 'COMPLETED'
        GROUP BY DATE(o.created_at)
        ORDER BY saleDate ASC
        """, nativeQuery = true)
    List<DailySalesProjection> findDailySales(@Param("storeAdminId") Long storeAdminId);

    // 5. Category Sales
    @Query(value = """
        SELECT 
            c.id AS categoryId,
            c.name AS categoryName,
            COALESCE(SUM(oi.quantity), 0) AS quantitySold,
            COALESCE(SUM(oi.price * oi.quantity), 0) AS totalSales
        FROM order_items oi
        JOIN products p ON oi.product_id = p.id
        JOIN categories c ON p.category_id = c.id
        JOIN orders o ON oi.order_id = o.id
        JOIN branches b ON o.branch_id = b.id
        WHERE b.store_admin_id = :storeAdminId AND o.status = 'COMPLETED'
        GROUP BY c.id, c.name
        ORDER BY totalSales DESC
        """, nativeQuery = true)
    List<CategorySalesProjection> findSalesByCategory(@Param("storeAdminId") Long storeAdminId);

    // 6. Payment Method Sales
    @Query(value = """
        SELECT 
            p.payment_method AS paymentMethod,
            COALESCE(SUM(p.amount), 0) AS totalAmount,
            COUNT(p.id) AS count
        FROM payments p
        JOIN orders o ON p.order_id = o.id
        JOIN branches b ON o.branch_id = b.id
        WHERE b.store_admin_id = :storeAdminId AND p.status = 'SUCCESS'
        GROUP BY p.payment_method
        """, nativeQuery = true)
    List<PaymentMethodProjection> findSalesByPaymentMethod(@Param("storeAdminId") Long storeAdminId);

    // 7 & 9. Branch Performance & Sales
    @Query(value = """
        SELECT 
            b.id AS branchId,
            b.name AS branchName,
            COALESCE(SUM(o.total_amount), 0) AS totalSales,
            COUNT(o.id) AS orderCount
        FROM branches b
        LEFT JOIN orders o ON o.branch_id = b.id AND o.status = 'COMPLETED'
        WHERE b.store_admin_id = :storeAdminId
        GROUP BY b.id, b.name
        """, nativeQuery = true)
    List<BranchSalesProjection> findBranchPerformance(@Param("storeAdminId") Long storeAdminId);

    // 8. Payment Breakdown
    @Query(value = """
        SELECT 
            p.payment_method AS paymentMethod,
            COUNT(p.id) AS totalTransactions,
            COALESCE(SUM(p.amount), 0) AS totalAmount,
            COUNT(CASE WHEN p.status = 'SUCCESS' THEN 1 END) AS successfulTransactions,
            COUNT(CASE WHEN p.status = 'FAILED' THEN 1 END) AS failedTransactions
        FROM payments p
        JOIN orders o ON p.order_id = o.id
        JOIN branches b ON o.branch_id = b.id
        WHERE b.store_admin_id = :storeAdminId
        GROUP BY p.payment_method
        """, nativeQuery = true)
    List<PaymentBreakdownProjection> findPaymentBreakdown(@Param("storeAdminId") Long storeAdminId);

    // 10. Alerts (Low Stock & Inactive Cashiers derived from DB)
    @Query(value = """
        SELECT 
            CONCAT('STK-', p.id) AS id,
            'LOW_STOCK' AS type,
            'WARNING' AS severity,
            CONCAT('Product "', p.name, '" is low on stock (', p.stock_quantity, ' remaining).') AS message,
            p.branch_id AS branchId,
            NOW() AS timestamp
        FROM products p
        JOIN branches b ON p.branch_id = b.id
        WHERE b.store_admin_id = :storeAdminId AND p.stock_quantity <= p.low_stock_threshold
        
        UNION ALL
        
        SELECT 
            CONCAT('USR-', u.id) AS id,
            'INACTIVE_CASHIER' AS type,
            'INFO' AS severity,
            CONCAT('Cashier ', u.name, ' has had no transactions recently.') AS message,
            u.branch_id AS branchId,
            NOW() AS timestamp
        FROM users u
        JOIN branches b ON u.branch_id = b.id
        WHERE b.store_admin_id = :storeAdminId 
          AND u.role = 'CASHIER' 
          AND u.id NOT IN (
              SELECT DISTINCT cashier_id FROM orders 
              WHERE created_at >= DATE_SUB(NOW(), INTERVAL 2 HOUR)
          )
        """, nativeQuery = true)
    List<AlertProjection> findActiveAlerts(@Param("storeAdminId") Long storeAdminId);
}