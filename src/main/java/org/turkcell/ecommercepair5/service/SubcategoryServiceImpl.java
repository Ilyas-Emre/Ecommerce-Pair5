package org.turkcell.ecommercepair5.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.entity.Subcategory;
import org.turkcell.ecommercepair5.repository.SubcategoryRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private SubcategoryRepository subcategoryRepository;
    @Override
    public Optional<Subcategory> findById(Integer id)
    {
        return subcategoryRepository.findById(id);
    }
}
