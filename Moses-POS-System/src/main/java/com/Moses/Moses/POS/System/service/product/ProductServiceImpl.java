package com.Moses.Moses.POS.System.service.product;

import com.Moses.Moses.POS.System.mapper.ProductMapper;
import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.modal.categoryModal.Category;
import com.Moses.Moses.POS.System.modal.productModal.Product;
import com.Moses.Moses.POS.System.modal.storeModal.Store;
import com.Moses.Moses.POS.System.payload.dto.ProductDto.ProductDTO;
import com.Moses.Moses.POS.System.repository.CategoryRepository;
import com.Moses.Moses.POS.System.repository.ProductRepository;
import com.Moses.Moses.POS.System.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public ProductDTO createProduct(ProductDTO productDTO, User user) throws Exception {
        Store store = storeRepository.findById(
                productDTO.getStoreId()
        ).orElseThrow(() -> new Exception("Store not found!")
                );
        Product product  = ProductMapper.toEntity(productDTO, store);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, User user) throws Exception {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Product not found!"));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setSku(productDTO.getSku());
        product.setImage(productDTO.getImage());
        product.setMrp(productDTO.getMrp());
        product.setSellingPrice(productDTO.getSellingPrice());
        product.setBrand(productDTO.getBrand());
        product.setUpdatedAt(LocalDateTime.now());

        if(productDTO.getCategoryId()!=null){
            Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(
                    ()-> new Exception("Category not found!")
            );
            product.setCategory(category);
        }


        Product savedProduct = productRepository.save(product);

        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public void deleteProduct(Long id, User user) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Product not found!")
        );
        productRepository.delete(product);

    }

    @Override
    public List<ProductDTO> getProductsByStoreId(Long storeId) {
        List<Product> products = productRepository.findByStoreId(storeId);
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> searchByKeyword(Long storeId, String keyword) {
        List<Product> products = productRepository.searchByKeyword(storeId, keyword);
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }
}
