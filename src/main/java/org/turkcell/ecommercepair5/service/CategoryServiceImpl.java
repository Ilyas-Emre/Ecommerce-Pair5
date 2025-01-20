package org.turkcell.ecommercepair5.service;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.entity.Category;
import org.turkcell.ecommercepair5.repository.CategoryRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }
}
=======
public class CategoryServiceImpl {
}
>>>>>>> 795569e52179b38f71c23b3864024f09d2056165
