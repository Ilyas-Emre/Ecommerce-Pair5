package org.turkcell.ecommercepair5.rules;

import org.springframework.stereotype.Component;
import org.turkcell.ecommercepair5.entity.Subcategory;
import org.turkcell.ecommercepair5.repository.SubcategoryRepository;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;

@Component
public class SubcategoryBusinessRules {
    private final SubcategoryRepository subcategoryRepository;

    public SubcategoryBusinessRules(SubcategoryRepository subcategoryRepository){
        this.subcategoryRepository = subcategoryRepository;
    }

    public Subcategory subcategoryMustExist(Integer id)
    {
        return subcategoryRepository.findById(id).orElseThrow(() -> new BusinessException("Category not found"));
    }

}
