package com.Moses.Moses.POS.System.payload.dto.branchDto;

import com.Moses.Moses.POS.System.payload.dto.StoreDto.StoreDTO;
import com.Moses.Moses.POS.System.payload.dto.UserDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class BranchDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private List<String> workingDays;
    private LocalTime openTime;
    private LocalTime closeTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private StoreDTO store;
    private Long storeId;

    private UserDto manager;
}
