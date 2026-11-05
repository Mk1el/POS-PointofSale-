package com.Moses.Moses.POS.System.modal.shiftReportModal;

import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.modal.branchModal.Branch;
import com.Moses.Moses.POS.System.modal.orderModal.Order;
import com.Moses.Moses.POS.System.modal.productModal.Product;
import com.Moses.Moses.POS.System.modal.refundModal.Refund;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShiftReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private Double totalSales;
    private Double totalRefunds;
    private Double netSale;
    private int totalOrders;
    @ManyToOne
    private User cashier;
    @ManyToOne
    private Branch branch;
    @Transient
    private List<PaymentSummary> paymentSummaries;
    @OneToMany(cascade =CascadeType.ALL)
    private List<Product> topSellingProducts;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> recentOrders;
    @OneToMany(mappedBy = "shiftReport",cascade=CascadeType.ALL)
    private List<Refund> refunds;
}
