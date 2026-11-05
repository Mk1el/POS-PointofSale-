package com.Moses.Moses.POS.System.mapper;

import com.Moses.Moses.POS.System.modal.orderModal.Order;
import com.Moses.Moses.POS.System.modal.productModal.Product;
import com.Moses.Moses.POS.System.modal.refundModal.Refund;
import com.Moses.Moses.POS.System.modal.shiftReportModal.ShiftReport;
import com.Moses.Moses.POS.System.payload.dto.ProductDto.ProductDTO;
import com.Moses.Moses.POS.System.payload.dto.orderDto.OrderDTO;
import com.Moses.Moses.POS.System.payload.dto.refundDto.RefundDTO;
import com.Moses.Moses.POS.System.payload.dto.shiftReportDto.ShiftReportDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ShiftReportMapper {
    public static ShiftReportDTO toDTO(ShiftReport entity){
        return ShiftReportDTO.builder()
                .id(entity.getId())
                .shiftEnd(entity.getShiftEnd())
                .shiftStart(entity.getShiftStart())
                .totalSales(entity.getTotalSales())
                .totalOrders(entity.getTotalOrders())
                .totalRefunds(entity.getTotalRefunds())
                .netSale(entity.getNetSale())
                .totalOrders(entity.getTotalOrders())
                .cashier(UserMapper.toDTO(entity.getCashier()))
                .cashierId(entity.getCashier().getId())
                .branchId(entity.getBranch().getId())
                .recentOrders(mapOrders(entity.getRecentOrders()))
                .topSellingProducts(mapProducts(entity.getTopSellingProducts()))
                .refunds(mapRefunds(entity.getRefunds()))
                .paymentSummaries(entity.getPaymentSummaries())
                .build();
    }

    private static List<RefundDTO> mapRefunds(List<Refund> refunds) {
        if(refunds == null || refunds.isEmpty()){
            return null;
        }
        return refunds.stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    private static List<ProductDTO> mapProducts(List<Product> topSellingProducts) {
        if (topSellingProducts==null || topSellingProducts.isEmpty()){
            return null;
        }
        return topSellingProducts.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    private static List<OrderDTO> mapOrders(List<Order> recentOrders) {
        if(recentOrders == null || recentOrders.isEmpty()){
            return null;
        }
        return recentOrders.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }
}
