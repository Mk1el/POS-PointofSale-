package com.Moses.Moses.POS.System.modal.orderModal;

import com.Moses.Moses.POS.System.modal.productModal.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Integer quantity;
    private Double price;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Order order;
}
