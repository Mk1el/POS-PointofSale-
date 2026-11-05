package com.Moses.Moses.POS.System.modal.shiftReportModal;

import com.Moses.Moses.POS.System.payload.payment.PaymentType;
import lombok.Data;

@Data

public class PaymentSummary {
    private PaymentType type;
    private Double totalAmount;
    private int transactionCount;
    private double percentage;
}
