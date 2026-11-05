package com.Moses.Moses.POS.System.payload.response;

import com.Moses.Moses.POS.System.payload.dto.UserDto;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserDto user;

}
