package com.Moses.Moses.POS.System.mapper;

import com.Moses.Moses.POS.System.modal.orderModal.Order;
import com.Moses.Moses.POS.System.modal.orderModal.OrderItem;
import com.Moses.Moses.POS.System.payload.dto.orderDto.OrderDTO;
import com.Moses.Moses.POS.System.payload.dto.orderDto.OrderItemDTO;

import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDTO toDTO(Order order){
        return OrderDTO.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .branchId(order.getBranch().getId())
                .cashier(UserMapper.toDTO(order.getCashier()))
                .customer(order.getCustomer())
                .paymentType(order.getPaymentType())
                .createdAt(order.getCreatedAt())
                .items(order.getItems().stream().map(OrderItemMapper::toDTO).collect(Collectors.toList()))
                .build();
    }
}
