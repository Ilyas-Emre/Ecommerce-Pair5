package org.turkcell.ecommercepair5.rules;

import org.springframework.stereotype.Component;
import org.turkcell.ecommercepair5.repository.CategoryRepository;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;

@Component
public class CategoryBusinessRules {
    private final CategoryRepository categoryRepository;

    public CategoryBusinessRules(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void categoryMustExist(Integer id)
    {
        categoryRepository.findById(id).orElseThrow(() -> new BusinessException("Category not found"));
    }
}
