package com.Moses.Moses.POS.System.modal.inventoryModal;

import com.Moses.Moses.POS.System.modal.branchModal.Branch;
import com.Moses.Moses.POS.System.modal.productModal.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Branch branch;
    @ManyToOne
    private Product product;
    @Column(nullable=false)
    private Integer quantity;
    private LocalDateTime lastUpdate;
    @PrePersist
    @PreUpdate
    private void onUpdate(){
        lastUpdate= LocalDateTime.now();
    }
}
