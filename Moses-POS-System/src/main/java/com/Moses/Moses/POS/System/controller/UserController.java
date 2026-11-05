package com.Moses.Moses.POS.System.controller;

import com.Moses.Moses.POS.System.exception.UserException;
import com.Moses.Moses.POS.System.mapper.UserMapper;
import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.payload.dto.UserDto;
import com.Moses.Moses.POS.System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(Principal principal)
            throws UserException {

        User user = userService.getUserByEmail(principal.getName());

        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable Long id
    ) throws UserException{
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> users = userService.getAllUsers()
                .stream()
                .map(UserMapper::toDTO)
                .toList();

        return ResponseEntity.ok(users);
    }
}
