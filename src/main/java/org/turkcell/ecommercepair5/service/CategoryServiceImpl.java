package org.turkcell.ecommercepair5.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turkcell.ecommercepair5.dto.category.CategoryListingDto;
import org.turkcell.ecommercepair5.dto.category.CreateCategoryDto;
import org.turkcell.ecommercepair5.dto.category.DeleteCategoryDto;
import org.turkcell.ecommercepair5.entity.Category;
import org.turkcell.ecommercepair5.repository.CategoryRepository;
import org.turkcell.ecommercepair5.rules.CategoryBusinessRules;
import org.turkcell.ecommercepair5.rules.ProductBusinessRules;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryBusinessRules categoryBusinessRules;
    private final ProductBusinessRules productBusinessRules;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductBusinessRules productBusinessRules, CategoryBusinessRules categoryBusinessRules) {
       this.categoryRepository = categoryRepository;
       this.productBusinessRules = productBusinessRules;
       this.categoryBusinessRules = categoryBusinessRules;
    }

    @Override
    public List<CategoryListingDto> getAll(){
        List<Category> rootCategories = categoryRepository.findAllByIsActiveTrue().stream()
                .filter(category -> category.getParent() == null)
                .collect(Collectors.toList());

        return rootCategories.stream()
                .map(this::convertToCategoryListingDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<CategoryListingDto> findCategoryById(Integer id) {
        Category category = categoryBusinessRules.categoryMustExist(id);

        // DTO'yu setter metodlarıyla dolduruyoruz
        CategoryListingDto categoryListingDto = convertToCategoryListingDto(category);

        return Optional.of(categoryListingDto);
    }

    @Override
    public void add(CreateCategoryDto createCategoryDto) {
        // Kategori zaten var mı kontrol et
        categoryBusinessRules.checkIfCategoryAlreadyExists(createCategoryDto.getName());

        // Yeni bir kategori oluştur
        Category category = new Category();
        category.setName(createCategoryDto.getName());
        category.setIsActive(true); // Varsayılan olarak aktif yap

        if (createCategoryDto.getParentId() != null) {
            Category parent = categoryBusinessRules.categoryMustExist(createCategoryDto.getParentId());
            category.setParent(parent);
        }

        categoryRepository.save(category);
    }

    @Transactional
    public void delete(DeleteCategoryDto deleteCategoryDto){

        Category category = categoryBusinessRules.categoryMustExist(deleteCategoryDto.getId());
        /*
        *Category category = this.findById(deleteCategoryDto.getId())
                .orElseThrow(()-> new RuntimeException("here is no category for this id."));
        */

        /*  Ürün varsa hata fırlatır, yoksa devam eder */
        productBusinessRules.hasProductsInCategory(deleteCategoryDto.getId());

        /*
        *if(productService.hasProductsInCategory(deleteCategoryDto.getId()))
            throw new RuntimeException("Category cannot be deleted as it has associated products!");
        */
        categoryBusinessRules.categoryHasSubcategories(deleteCategoryDto.getId());

        category.setIsActive(false);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryListingDto> getAllSubcategoriesByCategoryId(Integer categoryId){
        List<Category> subcategories = categoryRepository.findAllByParentId(categoryId);

        return subcategories.stream()
                .map(this::convertToCategoryListingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryListingDto> getAllSubcategories() {
        // Veritabanından sadece aktif olan kategorileri alıyoruz
        List<Category> categories = categoryRepository.findAllByIsActiveTrue();

        // Kategorileri DTO'ya dönüştür
        List<CategoryListingDto> categoryListingDtos = categories.stream()
                .map(this::convertToCategoryListingDto)
                .collect(Collectors.toList());

        return categoryListingDtos;
    }

    private CategoryListingDto convertToCategoryListingDto(Category category){
        List<CategoryListingDto> subcategoryDto = categoryRepository.findAllByParentId(category.getId())
                .stream()
                .map(subcategory -> new CategoryListingDto(subcategory.getId(), subcategory.getName(),null))
                .collect(Collectors.toList());

        return new CategoryListingDto(
                category.getId(),
                category.getName(),
                subcategoryDto /* alt kategorilerin listesini döndürür */
        );

    }
}
