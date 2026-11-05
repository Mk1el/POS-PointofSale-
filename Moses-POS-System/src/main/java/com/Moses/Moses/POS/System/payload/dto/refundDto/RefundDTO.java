package com.Moses.Moses.POS.System.payload.dto.refundDto;

import com.Moses.Moses.POS.System.modal.shiftReportModal.ShiftReport;
import com.Moses.Moses.POS.System.payload.dto.UserDto;
import com.Moses.Moses.POS.System.payload.dto.branchDto.BranchDTO;
import com.Moses.Moses.POS.System.payload.dto.orderDto.OrderDTO;
import com.Moses.Moses.POS.System.payload.payment.PaymentType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RefundDTO {
    private Long id;
    private OrderDTO order;
    private Long orderId;
    private String reason;
    private Double amount;
    private ShiftReport shiftReport;
    private Long shiftReportId;
    private UserDto cashier;
    private String cashierName;
    private BranchDTO branch;
    private Long branchId;
    private PaymentType paymentType;
    private LocalDateTime createdAt;
}
