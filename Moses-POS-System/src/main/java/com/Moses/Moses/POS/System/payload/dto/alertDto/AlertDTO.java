package com.Moses.Moses.POS.System.payload.dto.alertDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertDTO {
    private String id;
    private String type;         // "LOW_STOCK", "INACTIVE_CASHIER", etc.
    private String severity;     // "INFO", "WARNING", "CRITICAL"
    private String message;
    private Long branchId;
    private LocalDateTime timestamp;
}