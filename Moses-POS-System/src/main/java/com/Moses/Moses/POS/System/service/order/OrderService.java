package com.Moses.Moses.POS.System.service.order;

import com.Moses.Moses.POS.System.modal.orderModal.OrderStatus;
import com.Moses.Moses.POS.System.payload.dto.orderDto.OrderDTO;
import com.Moses.Moses.POS.System.payload.payment.PaymentType;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO) throws Exception;
    OrderDTO getOrderById(Long id) throws Exception;
    List<OrderDTO>getOrderByBranch(Long branchId, Long customerId, Long cashierId, PaymentType paymentType, OrderStatus status) throws Exception;
    List<OrderDTO> getOrderByCashier(Long cashierId);
    void deleteOrder(Long id) throws Exception;
    List<OrderDTO> getTodayOrdersByBranch(Long branchId) throws Exception;
    List<OrderDTO> getOrdersByCustomerId(Long customerId) throws Exception;
    List<OrderDTO> getTop5RecentOrdersByBranchId(Long branchId) throws Exception;

}
