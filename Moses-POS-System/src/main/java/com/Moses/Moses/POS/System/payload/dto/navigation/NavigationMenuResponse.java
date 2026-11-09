package com.Moses.Moses.POS.System.payload.dto.navigation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NavigationMenuResponse {

    private Long id;
    private String title;
    private String route;
    private String icon;
    private String description;
    private Integer displayOrder;
    private Long parentId;
}
