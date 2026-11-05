package com.Moses.Moses.POS.System.modal.refundModal;

import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.modal.branchModal.Branch;
import com.Moses.Moses.POS.System.modal.orderModal.Order;
import com.Moses.Moses.POS.System.modal.shiftReportModal.ShiftReport;
import com.Moses.Moses.POS.System.payload.payment.PaymentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Refund {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Order order;
    private String reason;
    @ManyToOne
    @JsonIgnore
    private ShiftReport shiftReport;
    @ManyToOne
    private User cashier;
    @ManyToOne
    private Branch branch;
    private Double amount;
    private PaymentType paymentType;
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
