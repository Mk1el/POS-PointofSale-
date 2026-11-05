package com.Moses.Moses.POS.System.payload.dto.shiftReportDto;

import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.modal.branchModal.Branch;
import com.Moses.Moses.POS.System.modal.orderModal.Order;
import com.Moses.Moses.POS.System.modal.productModal.Product;
import com.Moses.Moses.POS.System.modal.refundModal.Refund;
import com.Moses.Moses.POS.System.modal.shiftReportModal.PaymentSummary;
import com.Moses.Moses.POS.System.payload.dto.ProductDto.ProductDTO;
import com.Moses.Moses.POS.System.payload.dto.UserDto;
import com.Moses.Moses.POS.System.payload.dto.branchDto.BranchDTO;
import com.Moses.Moses.POS.System.payload.dto.orderDto.OrderDTO;
import com.Moses.Moses.POS.System.payload.dto.refundDto.RefundDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ShiftReportDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private Double totalSales;
    private Double totalRefunds;
    private Double netSale;
    private int totalOrders;
    private UserDto cashier;
    private Long cashierId;
    private Long branchId;
    private BranchDTO branch;

    private List<PaymentSummary> paymentSummaries;
    private List<ProductDTO> topSellingProducts;
    private List<OrderDTO> recentOrders;

    private List<RefundDTO> refunds;
}
