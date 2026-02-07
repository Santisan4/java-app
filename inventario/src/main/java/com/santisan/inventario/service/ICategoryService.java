package com.santisan.inventario.service;

import com.santisan.inventario.dto.CategoryDTO;
import com.santisan.inventario.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category findOrCreate(String name);
}
