package com.Moses.Moses.POS.System.service.navigation;

import com.Moses.Moses.POS.System.modal.UserRole;
import com.Moses.Moses.POS.System.modal.navigationMenuModal.NavigationMenu;
import com.Moses.Moses.POS.System.repository.NavigationMenuRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class NavigationMenuInitializer implements CommandLineRunner {

    private final NavigationMenuRepository navigationMenuRepository;

    @Override
    @Transactional
    public void run(String... args) {

        System.out.println("Initializing POS navigation menus...");

        // DASHBOARDS
        createMenu(
                "Dashboard",
                "/admin/dashboard",
                "LayoutDashboard",
                "System administration dashboard",
                1,
                null,
                UserRole.ROLE_ADMIN
        );

        createMenu(
                "Dashboard",
                "/store-admin/dashboard",
                "LayoutDashboard",
                "Store administration dashboard",
                1,
                null,
                UserRole.ROLE_STORE_ADMIN
        );

        createMenu(
                "Dashboard",
                "/store-manager/dashboard",
                "LayoutDashboard",
                "Store manager dashboard",
                1,
                null,
                UserRole.ROLE_STORE_MANAGER
        );

        createMenu(
                "Dashboard",
                "/branch-manager/dashboard",
                "LayoutDashboard",
                "Branch manager dashboard",
                1,
                null,
                UserRole.ROLE_BRANCH_MANAGER
        );

        createMenu(
                "Dashboard",
                "/cashier/dashboard",
                "LayoutDashboard",
                "Cashier dashboard",
                1,
                null,
                UserRole.ROLE_BRANCH_CASHIER
        );

        createMenu(
                "Dashboard",
                "/user/dashboard",
                "LayoutDashboard",
                "User dashboard",
                1,
                null,
                UserRole.ROLE_USER
        );

        // PRODUCT MANAGEMENT
        createMenu(
                "Products",
                "/products",
                "Package",
                "Manage store products",
                2,
                null,
                UserRole.ROLE_ADMIN,
                UserRole.ROLE_STORE_ADMIN,
                UserRole.ROLE_STORE_MANAGER
        );

        // CATEGORY MANAGEMENT
        createMenu(
                "Categories",
                "/categories",
                "Tags",
                "Manage product categories",
                3,
                null,
                UserRole.ROLE_ADMIN,
                UserRole.ROLE_STORE_ADMIN,
                UserRole.ROLE_STORE_MANAGER
        );

        // INVENTORY MANAGEMENT
        createMenu(
                "Inventory",
                "/inventory",
                "Warehouse",
                "Manage inventory and stock",
                4,
                null,
                UserRole.ROLE_ADMIN,
                UserRole.ROLE_STORE_ADMIN,
                UserRole.ROLE_STORE_MANAGER,
                UserRole.ROLE_BRANCH_MANAGER
        );

        // ORDER MANAGEMENT
        createMenu(
                "Orders",
                "/orders",
                "ShoppingCart",
                "View and manage orders",
                5,
                null,
                UserRole.ROLE_ADMIN,
                UserRole.ROLE_STORE_ADMIN,
                UserRole.ROLE_STORE_MANAGER,
                UserRole.ROLE_BRANCH_MANAGER,
                UserRole.ROLE_BRANCH_CASHIER,
                UserRole.ROLE_USER
        );

        // CUSTOMER MANAGEMENT
        createMenu(
                "Customers",
                "/customers",
                "Users",
                "Manage customer information",
                6,
                null,
                UserRole.ROLE_ADMIN,
                UserRole.ROLE_STORE_ADMIN,
                UserRole.ROLE_STORE_MANAGER
        );

        // SUPPLIER MANAGEMENT
        createMenu(
                "Suppliers",
                "/suppliers",
                "Truck",
                "Manage suppliers",
                7,
                null,
                UserRole.ROLE_ADMIN,
                UserRole.ROLE_STORE_ADMIN,
                UserRole.ROLE_STORE_MANAGER
        );

        // USER MANAGEMENT
        createMenu(
                "Users",
                "/users",
                "UserCog",
                "Manage system users and permissions",
                8,
                null,
                UserRole.ROLE_ADMIN,
                UserRole.ROLE_STORE_ADMIN
        );

        // REPORTS
        createMenu(
                "Reports",
                "/reports",
                "ChartNoAxesCombined",
                "View sales and operational reports",
                9,
                null,
                UserRole.ROLE_ADMIN,
                UserRole.ROLE_STORE_ADMIN,
                UserRole.ROLE_STORE_MANAGER,
                UserRole.ROLE_BRANCH_MANAGER
        );

        // SETTINGS
        createMenu(
                "Settings",
                "/settings",
                "Settings",
                "Manage application settings",
                10,
                null,
                UserRole.ROLE_ADMIN,
                UserRole.ROLE_STORE_ADMIN
        );

        System.out.println("POS navigation initialization completed.");
    }

    private void createMenu(
            String title,
            String route,
            String icon,
            String description,
            Integer displayOrder,
            Long parentId,
            UserRole... assignedRoles
    ) {

        NavigationMenu menu = navigationMenuRepository
                .findByRoute(route)
                .orElseGet(NavigationMenu::new);

        boolean isNew = menu.getId() == null;

        if (isNew) {
            menu.setTitle(title);
            menu.setRoute(route);
            menu.setIcon(icon);
            menu.setDescription(description);
            menu.setDisplayOrder(displayOrder);
            menu.setParentId(parentId);
            menu.setActive(true);
            menu.setRoles(EnumSet.noneOf(UserRole.class));
        }

        // Add missing role assignments without removing existing ones
        Set<UserRole> roles = menu.getRoles();

        if (roles == null) {
            roles = EnumSet.noneOf(UserRole.class);
        }

        for (UserRole role : assignedRoles) {
            roles.add(role);
        }

        menu.setRoles(roles);

        navigationMenuRepository.save(menu);

        System.out.println(
                (isNew ? "Created: " : "Updated: ")
                        + title
                        + " | Route: "
                        + route
                        + " | Roles: "
                        + roles
        );
    }
}
