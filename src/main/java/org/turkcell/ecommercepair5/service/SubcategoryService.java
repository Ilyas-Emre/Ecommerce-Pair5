package org.turkcell.ecommercepair5.service;

import org.turkcell.ecommercepair5.entity.Category;
import org.turkcell.ecommercepair5.entity.Subcategory;

import java.util.Optional;

public interface SubcategoryService {

    Optional<Subcategory> findById(Integer id);
}