package com.Moses.Moses.POS.System.service.product;

import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.payload.dto.ProductDto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO, User user) throws Exception;
    ProductDTO updateProduct(Long id, ProductDTO productDTO, User user) throws Exception;
    void deleteProduct(Long id, User user);
    List<ProductDTO> getProductsByStoreId(Long storeId);
    List <ProductDTO> searchByKeyword(Long storeId, String keyword);
}
