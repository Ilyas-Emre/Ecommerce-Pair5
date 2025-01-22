package org.turkcell.ecommercepair5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.turkcell.ecommercepair5.entity.Subcategory;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {
    List<Subcategory> findAllByCategoryId(Integer id);
    Optional<Subcategory> findByName(String name);
    List<Subcategory> findByCategoryIdAndIsActiveEquals(Integer categoryId, Boolean isActive);
    List<Subcategory> findByIsActiveEquals(Boolean isActive);
}
