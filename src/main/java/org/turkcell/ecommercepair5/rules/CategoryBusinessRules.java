package org.turkcell.ecommercepair5.rules;

import org.springframework.stereotype.Component;
import org.turkcell.ecommercepair5.entity.Category;
import org.turkcell.ecommercepair5.repository.CategoryRepository;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryBusinessRules {
    private final CategoryRepository categoryRepository;


    public CategoryBusinessRules(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category categoryMustExist(Integer id)
    {
        return categoryRepository.findById(id).orElseThrow(() -> new BusinessException("Category not found"));
    }

    public void categoryHasSubcategories(Integer id){
        List<Category> subcategories = categoryRepository.findAllByParentId(id);
        if (!subcategories.isEmpty()) {
            throw new RuntimeException("Category cannot be deleted as it has subcategories!");
        }
    }

    public void checkIfCategoryAlreadyExists(String categoryName) {
        Optional<Category> existingCategory = categoryRepository.findByName(categoryName);
        if (existingCategory.isPresent() && existingCategory.get().getIsActive()) {
            throw new BusinessException("Category with the same name already exists!");
        }
    }

}
