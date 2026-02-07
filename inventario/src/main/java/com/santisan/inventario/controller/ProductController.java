package com.santisan.inventario.controller;

import com.santisan.inventario.dto.ProductDTO;
import com.santisan.inventario.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody @Valid ProductDTO productToCreate){

        ProductDTO productCreated = productService.createProduct(productToCreate);
        return ResponseEntity.created(URI.create("/api/products/" + productCreated.getId()))
                .body(productCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,
                                             @RequestBody @Valid ProductDTO productDTO){
        ProductDTO productUpdated = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(productUpdated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> patchProduct(@PathVariable Long id,
                                                   @RequestBody ProductDTO productDTO){
        ProductDTO patchedProduct = productService.patchProduct(id, productDTO);
        return ResponseEntity.ok(patchedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
