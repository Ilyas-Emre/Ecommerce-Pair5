package org.turkcell.ecommercepair5.service;

import org.turkcell.ecommercepair5.entity.Category;

import java.util.Optional;

public interface CategoryService {

    Optional<Category> findById(Integer id);
}
