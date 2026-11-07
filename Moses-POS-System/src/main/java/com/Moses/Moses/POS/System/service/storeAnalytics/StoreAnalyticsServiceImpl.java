package com.Moses.Moses.POS.System.service.storeAnalytics;

import com.Moses.Moses.POS.System.mapper.StoreAnalyticsMapper;
import com.Moses.Moses.POS.System.payload.dto.alertDto.AlertDTO;
import com.Moses.Moses.POS.System.payload.dto.branchDto.BranchPerformanceDTO;
import com.Moses.Moses.POS.System.payload.dto.branchDto.BranchSalesDTO;
import com.Moses.Moses.POS.System.payload.dto.dailysalesDto.CategorySalesDTO;
import com.Moses.Moses.POS.System.payload.dto.dailysalesDto.DailySalesDTO;
import com.Moses.Moses.POS.System.payload.dto.dailysalesDto.PaymentBreakdownDTO;
import com.Moses.Moses.POS.System.payload.dto.salesTrendDto.MonthlySalesDTO;
import com.Moses.Moses.POS.System.payload.dto.salesTrendDto.SalesTrendDTO;
import com.Moses.Moses.POS.System.payload.dto.storeAnalyticsDto.OverviewKpiDTO;
import com.Moses.Moses.POS.System.payload.payment.PaymentMethodSalesDTO;
import com.Moses.Moses.POS.System.repository.StoreAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreAnalyticsServiceImpl implements StoreAnalyticsService {

    private final StoreAnalyticsRepository analyticsRepository;
    private final StoreAnalyticsMapper analyticsMapper;

    @Override
    public OverviewKpiDTO getOverview(Long storeAdminId) {
        var projection = analyticsRepository.findKpiSummary(storeAdminId);
        return analyticsMapper.toOverviewKpiDTO(projection);
    }

    @Override
    public List<SalesTrendDTO> getSalesTrends(Long storeAdminId, String period) {
        var projections = "weekly".equalsIgnoreCase(period)
                ? analyticsRepository.findWeeklySalesTrends(storeAdminId)
                : analyticsRepository.findDailySalesTrends(storeAdminId);

        return projections.stream()
                .map(analyticsMapper::toSalesTrendDTO)
                .toList();
    }

    @Override
    public List<MonthlySalesDTO> getMonthlySales(Long storeAdminId) {
        return analyticsRepository.findMonthlySales(storeAdminId).stream()
                .map(analyticsMapper::toMonthlySalesDTO)
                .toList();
    }

    @Override
    public List<DailySalesDTO> getDailySales(Long storeAdminId) {
        return analyticsRepository.findDailySales(storeAdminId).stream()
                .map(analyticsMapper::toDailySalesDTO)
                .toList();
    }

    @Override
    public List<CategorySalesDTO> getCategorySales(Long storeAdminId) {
        return analyticsRepository.findSalesByCategory(storeAdminId).stream()
                .map(analyticsMapper::toCategorySalesDTO)
                .toList();
    }

    @Override
    public List<PaymentMethodSalesDTO> getPaymentMethodSales(Long storeAdminId) {
        return analyticsRepository.findSalesByPaymentMethod(storeAdminId).stream()
                .map(analyticsMapper::toPaymentMethodSalesDTO)
                .toList();
    }

    @Override
    public List<BranchSalesDTO> getSalesByBranch(Long storeAdminId) {
        return analyticsRepository.findBranchPerformance(storeAdminId).stream()
                .map(analyticsMapper::toBranchSalesDTO)
                .toList();
    }

    @Override
    public List<PaymentBreakdownDTO> getPaymentBreakdown(Long storeAdminId) {
        return analyticsRepository.findPaymentBreakdown(storeAdminId).stream()
                .map(analyticsMapper::toPaymentBreakdownDTO)
                .toList();
    }

    @Override
    public List<BranchPerformanceDTO> getBranchPerformance(Long storeAdminId) {
        return analyticsRepository.findBranchPerformance(storeAdminId).stream()
                .map(analyticsMapper::toBranchPerformanceDTO)
                .toList();
    }

    @Override
    public List<AlertDTO> getAlerts(Long storeAdminId) {
        return analyticsRepository.findActiveAlerts(storeAdminId).stream()
                .map(analyticsMapper::toAlertDTO)
                .toList();
    }
}