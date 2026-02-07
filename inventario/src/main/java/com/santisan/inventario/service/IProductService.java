package com.santisan.inventario.service;

import com.santisan.inventario.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService{
    List<ProductDTO> getAllProducts();
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO patchProduct(Long id, ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
    ProductDTO getProductById(Long id);
}
