package com.Moses.Moses.POS.System.payload.dto.dashboardDto;

import java.time.LocalDate;

public record StoreRegistrationDTO (
        LocalDate date,
        long registrations
){}
