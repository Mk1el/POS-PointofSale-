
package com.Moses.Moses.POS.System.service.branchAnalytics;

import com.Moses.Moses.POS.System.payload.dto.dailysalesDto.*;
import com.Moses.Moses.POS.System.repository.BranchAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchAnalyticsServiceImpl implements BranchAnalyticsService {

    private final BranchAnalyticsRepository branchAnalyticsRepository;

    @Override
    public List<DailySalesDTO> getDailySales(
            Long branchId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();

        return branchAnalyticsRepository
                .findDailySales(branchId, start, end)
                .stream()
                .map(row -> new DailySalesDTO(
                        toLocalDate(row[0]),
                        toLong(row[1]),
                        toDouble(row[2])
                ))
                .toList();
    }

    @Override
    public List<DailySalesDTO> getDailySales(Long branchId, int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Days must be greater than zero");
        }

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days - 1L);

        return getDailySales(branchId, startDate, endDate);
    }

    @Override
    public List<TopProductDTO> getTopProducts(
            Long branchId,
            LocalDate date
    ) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return branchAnalyticsRepository
                .findTopProducts(branchId, start, end)
                .stream()
                .map(row -> new TopProductDTO(
                        toLong(row[0]),
                        (String) row[1],
                        toLong(row[2]),
                        toDouble(row[3]),
                        toDouble(row[4])
                ))
                .toList();
    }

    @Override
    public List<TopCashierDTO> getTopCashiers(
            Long branchId,
            LocalDate date
    ) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return branchAnalyticsRepository
                .findTopCashiers(branchId, start, end)
                .stream()
                .map(row -> new TopCashierDTO(
                        toLong(row[0]),
                        (String) row[1],
                        toLong(row[2]),
                        toDouble(row[3])
                ))
                .toList();
    }

    @Override
    public List<CategorySalesDTO> getCategorySales(
            Long branchId,
            LocalDate date
    ) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return branchAnalyticsRepository
                .findCategorySales(branchId, start, end)
                .stream()
                .map(row -> new CategorySalesDTO(
                        toLong(row[0]),
                        (String) row[1],
                        toLong(row[2]),
                        toDouble(row[3])
                ))
                .toList();
    }

    @Override
    public TodayOverviewDTO getTodayOverview(Long branchId) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        Long totalOrders = branchAnalyticsRepository.countOrders(
                branchId, start, end
        );

        Double totalSales = branchAnalyticsRepository.sumSales(
                branchId, start, end
        );

        Long uniqueCustomers = branchAnalyticsRepository.countUniqueCustomers(
                branchId, start, end
        );

        Long itemsSold = branchAnalyticsRepository.sumItemsSold(
                branchId, start, end
        );

        double averageOrderValue = totalOrders == null || totalOrders == 0
                ? 0.0
                : toDouble(totalSales) / totalOrders;

        return new TodayOverviewDTO(
                totalOrders == null ? 0L : totalOrders,
                toDouble(totalSales),
                uniqueCustomers == null ? 0L : uniqueCustomers,
                itemsSold == null ? 0L : itemsSold,
                averageOrderValue
        );
    }

    @Override
    public List<PaymentBreakdownDTO> getPaymentBreakdown(
            Long branchId,
            LocalDate date
    ) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return branchAnalyticsRepository
                .findPaymentBreakdown(branchId, start, end)
                .stream()
                .map(row -> new PaymentBreakdownDTO(
                        row[0] == null ? "UNKNOWN" : row[0].toString(),
                        toLong(row[1]),
                        toDouble(row[2])
                ))
                .toList();
    }

    private LocalDate toLocalDate(Object value) {
        if (value instanceof Date sqlDate) {
            return sqlDate.toLocalDate();
        }

        if (value instanceof LocalDate localDate) {
            return localDate;
        }

        throw new IllegalArgumentException(
                "Unsupported date result type: " +
                        (value == null ? "null" : value.getClass().getName())
        );
    }

    private Long toLong(Object value) {
        return value == null ? 0L : ((Number) value).longValue();
    }

    private Double toDouble(Object value) {
        return value == null ? 0.0 : ((Number) value).doubleValue();
    }
}