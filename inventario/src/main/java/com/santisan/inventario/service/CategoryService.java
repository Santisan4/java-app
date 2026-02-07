package com.santisan.inventario.service;

import com.santisan.inventario.dto.CategoryDTO;
import com.santisan.inventario.model.Category;
import com.santisan.inventario.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .toList();
    }

    @Override
    public Category findOrCreate(String name){
        return categoryRepository.findByName(name)
                .orElseGet(() -> {
                    Category category = new Category();
                    category.setName(name);
                    return categoryRepository.save(category);
                });
    }
}
