package org.turkcell.ecommercepair5.rules;

import org.springframework.stereotype.Component;
import org.turkcell.ecommercepair5.repository.ProductRepository;
import org.turkcell.ecommercepair5.service.ProductService;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;

@Component
public class ProductBusinessRules {

    private final ProductRepository productRepository;

    public ProductBusinessRules(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    public void checkProductsExistInCategory(Integer id){
        if(productRepository.existsByCategoryId(id))
            throw new BusinessException("Products exist in this category, cannot delete.");
    }

    public void hasProductsInCategory(Integer id){
        if(productRepository.existsByCategoryId(id))
            throw new RuntimeException("Category cannot be deleted as it has associated products!");
    }
}
