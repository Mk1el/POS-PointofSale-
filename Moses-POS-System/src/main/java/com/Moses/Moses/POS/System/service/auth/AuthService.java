package com.Moses.Moses.POS.System.service.auth;

import com.Moses.Moses.POS.System.exception.UserException;
import com.Moses.Moses.POS.System.payload.dto.UserDto;
import com.Moses.Moses.POS.System.payload.dto.UserLoginDto;
import com.Moses.Moses.POS.System.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(UserDto userDto) throws UserException;
    AuthResponse login(UserLoginDto userLoginDto);
}
