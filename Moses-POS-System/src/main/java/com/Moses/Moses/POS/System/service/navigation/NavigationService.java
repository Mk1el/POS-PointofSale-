package com.Moses.Moses.POS.System.service.navigation;

import com.Moses.Moses.POS.System.modal.UserRole;
import com.Moses.Moses.POS.System.modal.navigationMenuModal.NavigationMenu;
import com.Moses.Moses.POS.System.payload.dto.navigation.NavigationMenuResponse;
import com.Moses.Moses.POS.System.repository.NavigationMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NavigationService {
    private final NavigationMenuRepository navigationMenuRepository;

    public List<NavigationMenuResponse> getMenusByRole(UserRole role) {

        List<NavigationMenu> menus = navigationMenuRepository.findDistinctByRolesContainingAndActiveTrueOrderByDisplayOrderAsc(
                                role
                        );

        return menus.stream()
                .map(menu -> NavigationMenuResponse.builder()
                        .id(menu.getId())
                        .title(menu.getTitle())
                        .route(menu.getRoute())
                        .icon(menu.getIcon())
                        .description(menu.getDescription())
                        .displayOrder(menu.getDisplayOrder())
                        .parentId(menu.getParentId())
                        .build())
                .toList();
    }
}
