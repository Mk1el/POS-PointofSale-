package com.Moses.Moses.POS.System.controller;


import com.Moses.Moses.POS.System.exception.UserException;
import com.Moses.Moses.POS.System.payload.dto.UserDto;
import com.Moses.Moses.POS.System.payload.dto.UserLoginDto;
import com.Moses.Moses.POS.System.payload.response.AuthResponse;
import com.Moses.Moses.POS.System.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupHandler(
            @RequestBody UserDto userDTO
            )throws UserException {
        return ResponseEntity.ok( authService.signup(userDTO));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody UserLoginDto userLoginDto
    )throws UserException {
        return ResponseEntity.ok(authService.login(userLoginDto));
    }


}
