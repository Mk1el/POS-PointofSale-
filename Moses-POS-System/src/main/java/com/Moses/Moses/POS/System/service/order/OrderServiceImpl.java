package com.Moses.Moses.POS.System.service.order;

import com.Moses.Moses.POS.System.mapper.OrderMapper;
import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.modal.branchModal.Branch;
import com.Moses.Moses.POS.System.modal.orderModal.Order;
import com.Moses.Moses.POS.System.modal.orderModal.OrderItem;
import com.Moses.Moses.POS.System.modal.orderModal.OrderStatus;
import com.Moses.Moses.POS.System.modal.productModal.Product;
import com.Moses.Moses.POS.System.payload.dto.orderDto.OrderDTO;
import com.Moses.Moses.POS.System.payload.payment.PaymentType;
import com.Moses.Moses.POS.System.repository.OrderItemRepository;
import com.Moses.Moses.POS.System.repository.OrderRepository;
import com.Moses.Moses.POS.System.repository.ProductRepository;
import com.Moses.Moses.POS.System.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) throws Exception {
        User cashier = userService.getCurrentUser();
        Branch branch = cashier.getBranch();
        if(branch == null){
            throw new Exception("Cashier branch not found!");
        }
        Order order = Order.builder()
                .branch(branch)
                .cashier(cashier)
                .customer(orderDTO.getCustomer())
                .paymentType(orderDTO.getPaymentType())
                .build();
        orderRepository.save(order);
        List<OrderItem> orderItems = orderDTO.getItems().stream().map(
                itemDTO ->{
                    Product product = productRepository.findById(itemDTO.getProductId())
                            .orElseThrow(() -> new EntityNotFoundException("Product not found!"));
                    //                    return orderItemRepository.save(orderItem);
                    return OrderItem.builder()
                            .product(product)
                            .quantity(itemDTO.getQuantity())
                            .price(product.getSellingPrice()* itemDTO.getQuantity())
                            .order(order)
                            .build();
                }
        ).toList();
        double total = orderItems.stream().mapToDouble(
                OrderItem::getPrice
        ).sum();
        order.setTotalAmount(total);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderDTO getOrderById(Long id) throws Exception {
        return orderRepository.findById(id)
                .map(OrderMapper::toDTO).orElseThrow(
                ()-> new Exception("Order not found with id"+id)
        );
    }

    @Override
    public List<OrderDTO> getOrderByBranch(Long branchId, Long customerId, Long cashierId, PaymentType paymentType, OrderStatus status) throws Exception {
        return orderRepository.findByBranchId(branchId)
                .stream().filter(order ->customerId == null || (order.getCustomer()!=null && order.getCustomer().getId().equals(customerId)))
                .filter(order-> cashierId==null || order.getCashier()!=null && order.getCashier().getId().equals(cashierId))
                .filter(order -> paymentType==null || order.getPaymentType()==paymentType)
                .map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrderByCashier(Long cashierId) {
        return orderRepository.findByCashierId(cashierId).stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        Order order = orderRepository.findById(id).orElseThrow(
                ()-> new Exception("Order not found with id"+id)
        );
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDTO> getTodayOrdersByBranch(Long branchId) throws Exception {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();
        return orderRepository.findByBranchIdAndCreatedAtBetween(
                branchId, start, end
        ).stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByCustomerId(Long customerId) throws Exception {
        return orderRepository.findByCustomerId(customerId).stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getTop5RecentOrdersByBranchId(Long branchId) throws Exception {
        return orderRepository.findTop5ByBranchIdOrderByCreatedAtDesc(branchId)
                .stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }
}
