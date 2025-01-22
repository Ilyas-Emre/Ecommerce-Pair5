package org.turkcell.ecommercepair5.service;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turkcell.ecommercepair5.dto.category.CategoryListingDto;
import org.turkcell.ecommercepair5.dto.category.CreateCategoryDto;
import org.turkcell.ecommercepair5.dto.category.DeleteCategoryDto;
import org.turkcell.ecommercepair5.entity.Category;
import org.turkcell.ecommercepair5.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubcategoryService subcategoryService;
    private final ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, @Lazy SubcategoryService subcategoryService, @Lazy ProductService productService) {
       this.categoryRepository = categoryRepository;
       this.subcategoryService = subcategoryService;
       this.productService = productService;
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void add(CreateCategoryDto createCategoryDto) {
        Optional<Category> existingCategory = categoryRepository.findByName(createCategoryDto.getName());

        existingCategory.ifPresentOrElse(category -> {
            if (!category.getIsActive()) {
                // Kategori pasifse, aktif hale getir
                category.setIsActive(true);
                categoryRepository.save(category);
            } else {
                // Kategori zaten aktifse hata fırlat
                throw new RuntimeException("Category with the same name already exists!");
            }
        }, () -> {
            // Yeni bir kategori oluştur
            Category category = new Category();
            category.setName(createCategoryDto.getName());
            category.setIsActive(true); // Varsayılan olarak aktif yap
            categoryRepository.save(category);
        });
    }

    @Transactional
    public void delete(DeleteCategoryDto deleteCategoryDto){
        Category category = this.findById(deleteCategoryDto.getId())
                .orElseThrow(()-> new RuntimeException("here is no category for this id."));

        // kategoriye ait ürünler var mı kontrol
        if(productService.hasProductsInCategory(deleteCategoryDto.getId()))
            throw new RuntimeException("Category cannot be deleted as it has associated products!");

        category.setIsActive(false);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryListingDto> getAll() {
        // Veritabanından sadece aktif olan kategorileri alıyoruz
        List<Category> categories = categoryRepository.findAllByIsActiveTrue();

        // Kategorileri DTO'ya dönüştür
        List<CategoryListingDto> categoryListingDtos = categories.stream()
                .map(category -> new CategoryListingDto(
                        category.getId(),
                        category.getName(),
                        subcategoryService.listingSubcategoriesByCategoryId(category.getId()))) // Subcategory'leri de alıyoruz
                .collect(Collectors.toList());

        return categoryListingDtos;
    }
}
