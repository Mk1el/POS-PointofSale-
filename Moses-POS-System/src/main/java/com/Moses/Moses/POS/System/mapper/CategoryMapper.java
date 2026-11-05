package com.Moses.Moses.POS.System.mapper;

import com.Moses.Moses.POS.System.modal.categoryModal.Category;
import com.Moses.Moses.POS.System.payload.dto.categoryDto.CategoryDTO;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .storeId(category.getStore()!=null?category.getStore().getId():null)
                .build();
    }
}
