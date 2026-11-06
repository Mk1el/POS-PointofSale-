package com.Moses.Moses.POS.System.mapper;

import com.Moses.Moses.POS.System.payload.dto.dashboardDto.DashboardSummaryDTO;
import com.Moses.Moses.POS.System.payload.dto.dashboardDto.StoreRegistrationDTO;
import com.Moses.Moses.POS.System.payload.dto.dashboardDto.StoreStatusDistributionDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AdminDashboardMapper {
    public DashboardSummaryDTO toSummaryDTO(
            long totalStores,
            long activeStores,
            long pendingStores,
            long blockedStores
    ){
        return new DashboardSummaryDTO(
                totalStores,
                activeStores,
                pendingStores,
                blockedStores
        );
    }
    public StoreRegistrationDTO toRegistrationDTO(
            LocalDate date,
            long registrations
    ){
        return new StoreRegistrationDTO(
                date,
                registrations
        );
    }
    public StoreStatusDistributionDTO toStatusDistributionDTO(
            long active,
            long blocked,
            long pending
    ){
        return new StoreStatusDistributionDTO(
                active, blocked, pending
        );
    }
}
