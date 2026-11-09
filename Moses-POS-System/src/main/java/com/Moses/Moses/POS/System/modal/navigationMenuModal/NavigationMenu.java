package com.Moses.Moses.POS.System.modal.navigationMenuModal;

import com.Moses.Moses.POS.System.modal.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "navigation_menus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NavigationMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String route;
    private String icon;
    private String description;
    private Integer displayOrder;
    @Builder.Default
    private boolean active = true;
    private Long parentId;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "navigation_menu_roles",
            joinColumns = @JoinColumn(name="menu_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    @Builder.Default
    private Set<UserRole> roles = new HashSet<>();
}
