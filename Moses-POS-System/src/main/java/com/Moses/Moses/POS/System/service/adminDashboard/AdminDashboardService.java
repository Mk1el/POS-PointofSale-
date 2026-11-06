package com.Moses.Moses.POS.System.service.adminDashboard;

import com.Moses.Moses.POS.System.payload.dto.dashboardDto.DashboardSummaryDTO;
import com.Moses.Moses.POS.System.payload.dto.dashboardDto.StoreRegistrationDTO;
import com.Moses.Moses.POS.System.payload.dto.dashboardDto.StoreStatusDistributionDTO;

import java.util.List;

public interface AdminDashboardService {
    DashboardSummaryDTO getDashboardSummary();
    List<StoreRegistrationDTO> getStoreRegistrations();
    StoreStatusDistributionDTO getStoreStatusDistribution();
}
