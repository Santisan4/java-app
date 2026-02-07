package com.santisan.inventario.service;

import com.santisan.inventario.dto.CategoryDTO;
import com.santisan.inventario.dto.ProductDTO;
import com.santisan.inventario.exception.NotFoundException;
import com.santisan.inventario.mapper.Mapper;
import com.santisan.inventario.model.Category;
import com.santisan.inventario.model.Product;
import com.santisan.inventario.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @Transactional
    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Optional<Product> existingProduct =
                productRepository.findByName(productDTO.getName());

        Product product;

        if (existingProduct.isPresent()) {
            // 👉 Si el producto ya existe, solo aumenta stock
            product = existingProduct.get();
            product.setStock(product.getStock() + productDTO.getStock());

        } else {
            // 👉 Si no existe, se crea
            product = new Product();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setStock(productDTO.getStock());
            product.setPrice(productDTO.getPrice());

            Set<Category> categories = new HashSet<>();

            if (productDTO.getCategories() != null) {
                for (CategoryDTO categoryDTO : productDTO.getCategories()) {
                    Category category =
                            categoryService.findOrCreate(categoryDTO.getName());
                    categories.add(category);
                }
            }

            product.setCategory(categories);
        }

        Product savedProduct = productRepository.save(product);
        return Mapper.toDTO(savedProduct);
    }

    // Metodo para actualizar parcialmente
    @Transactional
    @Override
    public ProductDTO patchProduct(Long id, ProductDTO productToPatch){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if(productToPatch.getName() != null && !productToPatch.getName().equals(product.getName())){

            if(productRepository.findByName(productToPatch.getName()).isPresent()){
                throw new IllegalStateException("Product name already exists");
            }

            product.setName(productToPatch.getName());
        }

        if(productToPatch.getDescription() != null){
            product.setDescription(productToPatch.getDescription());
        }

        if(productToPatch.getPrice() != null){
            product.setPrice(productToPatch.getPrice());
        }

        if(productToPatch.getStock() != null){
            product.setStock(productToPatch.getStock());
        }

        return Mapper.toDTO(productRepository.save(product));
    }

    // Metodo para actualizar completo / crear
    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product prod = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        prod.setName(productDTO.getName());
        prod.setDescription(productDTO.getDescription());
        prod.setStock(productDTO.getStock());
        prod.setPrice(productDTO.getPrice());

        return Mapper.toDTO(productRepository.save(prod));
    }

    @Override
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) throw new NotFoundException("Product not found");

        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id " + id));

        return Mapper.toDTO(product);
    }

}
