package com.Moses.Moses.POS.System.service.category;

import com.Moses.Moses.POS.System.exception.UserException;
import com.Moses.Moses.POS.System.mapper.CategoryMapper;
import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.modal.UserRole;
import com.Moses.Moses.POS.System.modal.categoryModal.Category;
import com.Moses.Moses.POS.System.modal.storeModal.Store;
import com.Moses.Moses.POS.System.payload.dto.categoryDto.CategoryDTO;
import com.Moses.Moses.POS.System.repository.CategoryRepository;
import com.Moses.Moses.POS.System.repository.StoreRepository;
import com.Moses.Moses.POS.System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final StoreRepository storeRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO dto) throws Exception {
        User user = userService.getCurrentUser();
        Store store = storeRepository.findById(dto.getStoreId()).orElseThrow(
                ()-> new Exception("Store not found!")
        );
        Category category = Category.builder()
                .store(store)
                .name(dto.getName())
                .build();
        checkAuthority(user, category.getStore());
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDTO> getCategoriesByStore(Long storeId) {
        List<Category> categories = categoryRepository.findByStoreId(storeId);
        return categories.stream().map(CategoryMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new Exception("Category does not exist!")
        );
        User user = userService.getCurrentUser();
        category.setName(dto.getName());
        checkAuthority(user, category.getStore());
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new Exception("Category does not exist!")
        );
        User user = userService.getCurrentUser();
        checkAuthority(user, category.getStore());
        categoryRepository.delete(category);

    }
    private void checkAuthority(User user, Store store) throws Exception {
        boolean isAdmin = user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
        boolean isManager = user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
        boolean isSameStore = user.equals(store.getStoreAdmin());

        if(!(isAdmin && isSameStore) && !isManager){
            throw new Exception("You do not have permission to manage this category!");
        }
    }
}
