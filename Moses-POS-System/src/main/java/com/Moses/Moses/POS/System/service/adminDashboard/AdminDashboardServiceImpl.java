package com.Moses.Moses.POS.System.service.adminDashboard;

import com.Moses.Moses.POS.System.mapper.AdminDashboardMapper;
import com.Moses.Moses.POS.System.modal.storeModal.StoreStatus;
import com.Moses.Moses.POS.System.payload.dto.dashboardDto.DashboardSummaryDTO;
import com.Moses.Moses.POS.System.payload.dto.dashboardDto.StoreRegistrationDTO;
import com.Moses.Moses.POS.System.payload.dto.dashboardDto.StoreStatusDistributionDTO;
import com.Moses.Moses.POS.System.repository.StoreDashboardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AdminDashboardServiceImpl implements  AdminDashboardService{
    private final StoreDashboardRepository storeRepository;
    private final AdminDashboardMapper dashboardMapper;

    public AdminDashboardServiceImpl(
            StoreDashboardRepository storeRepository,
            AdminDashboardMapper dashboardMapper
    ){
        this.storeRepository = storeRepository;
        this.dashboardMapper = dashboardMapper;
    }
    @Override
    public DashboardSummaryDTO getDashboardSummary() {
        long totalSales = storeRepository.count();
        long activeStores = storeRepository.countByStatus(StoreStatus.ACTIVE);
        long pendingStores = storeRepository.countByStatus(StoreStatus.PENDING);
        long blockedStores = storeRepository.countByStatus(StoreStatus.BLOCKED);
        return dashboardMapper.toSummaryDTO(
                totalSales, activeStores, pendingStores, blockedStores
        );
    }

    @Override
    public List<StoreRegistrationDTO> getStoreRegistrations() {
        List<StoreRegistrationDTO> registrations = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for(int i =6; i>=0; i--){
            LocalDate date = today.minusDays(i);
            LocalDateTime startDate = date.atStartOfDay();
            LocalDateTime endDate = date.plusDays(1).atStartOfDay();
            long registrationCount = storeRepository.countRegistrationsBetween(startDate, endDate);
            registrations.add(dashboardMapper.toRegistrationDTO(
                    date, registrationCount
            )
            );
        }
        return registrations;
    }

    @Override
    public StoreStatusDistributionDTO getStoreStatusDistribution() {
        long active = storeRepository.countByStatus(StoreStatus.ACTIVE);
        long blocked = storeRepository.countByStatus(StoreStatus.BLOCKED);
        long pending = storeRepository.countByStatus(StoreStatus.PENDING);
        return dashboardMapper.toStatusDistributionDTO(
                active, blocked, pending
        );
    }
}
