package org.turkcell.ecommercepair5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.turkcell.ecommercepair5.dto.category.CategoryListingDto;
import org.turkcell.ecommercepair5.entity.Category;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByIsActiveTrue();
    Optional<Category> findByName(String name);
}

