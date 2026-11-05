package com.Moses.Moses.POS.System.payload.dto.StoreDto;

import com.Moses.Moses.POS.System.modal.storeModal.StoreContact;
import com.Moses.Moses.POS.System.modal.storeModal.StoreStatus;
import com.Moses.Moses.POS.System.payload.dto.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreDTO {
    private Long id;
    private String brand;
    private UserDto storeAdmin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String description;
    private String storeType;
    private StoreStatus status;
    private StoreContact contact;
}
