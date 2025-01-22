package org.turkcell.ecommercepair5.service;

import org.turkcell.ecommercepair5.dto.subcategory.CreateSubcategoryDto;
import org.turkcell.ecommercepair5.dto.subcategory.DeleteSubcategoryDto;
import org.turkcell.ecommercepair5.dto.subcategory.SubcategoryListingDto;
import org.turkcell.ecommercepair5.entity.Category;
import org.turkcell.ecommercepair5.entity.Subcategory;

import java.util.List;
import java.util.Optional;

public interface SubcategoryService {

    Optional<Subcategory> findById(Integer id);

    Optional<SubcategoryListingDto> findCategoryById(Integer id);

    void add(CreateSubcategoryDto createSubcategoryDto);

    void delete(DeleteSubcategoryDto deleteSubcategoryDto);

    List<SubcategoryListingDto> getAll();

    List<SubcategoryListingDto> listingSubcategoriesByCategoryId(Integer categoryId);
}
