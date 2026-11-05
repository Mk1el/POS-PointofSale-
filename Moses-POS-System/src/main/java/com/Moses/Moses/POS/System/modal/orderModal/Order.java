package com.Moses.Moses.POS.System.modal.orderModal;

import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.modal.branchModal.Branch;
import com.Moses.Moses.POS.System.modal.customerModal.Customer;
import com.Moses.Moses.POS.System.payload.payment.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Double totalAmount;
    private LocalDateTime createdAt;
    @ManyToOne
    private Branch branch;
    @ManyToOne
    private User cashier;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "order", cascade=CascadeType.ALL)
    private List<OrderItem> items;
    private PaymentType paymentType;
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
    
}
