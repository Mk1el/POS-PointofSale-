package com.Moses.Moses.POS.System.controller;

import com.Moses.Moses.POS.System.modal.UserRole;
import com.Moses.Moses.POS.System.payload.dto.navigation.NavigationMenuResponse;
import com.Moses.Moses.POS.System.service.navigation.NavigationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/navigation")
@RequiredArgsConstructor
public class NavigationController {

    private final NavigationService navigationService;

    @GetMapping("/my-menu")
    public ResponseEntity<List<NavigationMenuResponse>> getMyMenu(
            Authentication authentication) {

        UserRole role = authentication.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .map(UserRole::valueOf)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("No role assigned"));

        return ResponseEntity.ok(
                navigationService.getMenusByRole(role)
        );
    }
}
