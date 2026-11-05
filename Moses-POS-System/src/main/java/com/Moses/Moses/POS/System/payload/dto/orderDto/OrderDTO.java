package com.Moses.Moses.POS.System.payload.dto.orderDto;

import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.modal.branchModal.Branch;
import com.Moses.Moses.POS.System.modal.customerModal.Customer;
import com.Moses.Moses.POS.System.modal.orderModal.OrderItem;
import com.Moses.Moses.POS.System.payload.dto.UserDto;
import com.Moses.Moses.POS.System.payload.dto.branchDto.BranchDTO;
import com.Moses.Moses.POS.System.payload.payment.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderDTO {
        private Long id;
        private Double totalAmount;
        private LocalDateTime createdAt;
        private Long branchId;
        private Long customerId;
        private BranchDTO branch;
        private UserDto cashier;
        private Customer customer;
        private List<OrderItemDTO> items;
        private PaymentType paymentType;

           }
