package org.turkcell.ecommercepair5.service;


import org.turkcell.ecommercepair5.dto.category.CategoryListingDto;
import org.turkcell.ecommercepair5.dto.category.CreateCategoryDto;
import org.turkcell.ecommercepair5.dto.category.DeleteCategoryDto;
import org.turkcell.ecommercepair5.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> findById(Integer id);

    List<CategoryListingDto> getAllSubcategories();

    List<CategoryListingDto> getAllSubcategoriesByCategoryId(Integer categoryId);

    Optional<CategoryListingDto> findCategoryById(Integer id);

    void add(CreateCategoryDto createCategoryDto);

    void delete (DeleteCategoryDto deleteCategoryDto);

    List<CategoryListingDto> getAll();
}
