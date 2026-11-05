package com.Moses.Moses.POS.System.repository;

import com.Moses.Moses.POS.System.modal.orderModal.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
