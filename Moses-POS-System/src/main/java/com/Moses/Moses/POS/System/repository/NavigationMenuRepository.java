package com.Moses.Moses.POS.System.repository;

import com.Moses.Moses.POS.System.modal.UserRole;
import com.Moses.Moses.POS.System.modal.navigationMenuModal.NavigationMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NavigationMenuRepository extends JpaRepository<NavigationMenu, Long> {
    List<NavigationMenu> findDistinctByRolesContainingAndActiveTrueOrderByDisplayOrderAsc(
            UserRole role
    );
    Optional<NavigationMenu> findByRoute(String route);
}
