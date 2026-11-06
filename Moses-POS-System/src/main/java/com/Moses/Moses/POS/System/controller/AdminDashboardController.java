
package com.Moses.Moses.POS.System.controller;

import com.Moses.Moses.POS.System.payload.dto.dashboardDto.DashboardSummaryDTO;
import com.Moses.Moses.POS.System.payload.dto.dashboardDto.StoreRegistrationDTO;
import com.Moses.Moses.POS.System.payload.dto.dashboardDto.StoreStatusDistributionDTO;
import com.Moses.Moses.POS.System.service.adminDashboard.AdminDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/super-admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    public AdminDashboardController(
            AdminDashboardService dashboardService
    ) {
        this.dashboardService = dashboardService;
    }

    // 1. Dashboard Summary
    @GetMapping("/dashboard/summary")
    public ResponseEntity<DashboardSummaryDTO> getDashboardSummary() {

        return ResponseEntity.ok(
                dashboardService.getDashboardSummary()
        );
    }

    // 2. Store Registrations - Last 7 Days
    @GetMapping("/dashboard/store-registrations")
    public ResponseEntity<List<StoreRegistrationDTO>> getStoreRegistrations() {

        return ResponseEntity.ok(
                dashboardService.getStoreRegistrations()
        );
    }

    // 3. Store Status Distribution
    @GetMapping("/dashboard/store-status-distribution")
    public ResponseEntity<StoreStatusDistributionDTO> getStoreStatusDistribution() {

        return ResponseEntity.ok(
                dashboardService.getStoreStatusDistribution()
        );
    }
}
