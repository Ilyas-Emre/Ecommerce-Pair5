package org.turkcell.ecommercepair5.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turkcell.ecommercepair5.dto.subcategory.CreateSubcategoryDto;
import org.turkcell.ecommercepair5.dto.subcategory.DeleteSubcategoryDto;
import org.turkcell.ecommercepair5.dto.subcategory.SubcategoryListingDto;
import org.turkcell.ecommercepair5.entity.Category;
import org.turkcell.ecommercepair5.entity.Subcategory;
import org.turkcell.ecommercepair5.repository.SubcategoryRepository;
import org.turkcell.ecommercepair5.rules.CategoryBusinessRules;
import org.turkcell.ecommercepair5.rules.ProductBusinessRules;
import org.turkcell.ecommercepair5.rules.SubcategoryBusinessRules;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;

    // private final ProductService productService;
    // private final CategoryService categoryService;
    private final SubcategoryBusinessRules subcategoryBusinessRules;
    private final ProductBusinessRules productBusinessRules;
    private final CategoryBusinessRules categoryBusinessRules;

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, @Lazy ProductService productService, @Lazy CategoryService categoryService, SubcategoryBusinessRules subcategoryBusinessRules, ProductBusinessRules productBusinessRules, CategoryBusinessRules categoryBusinessRules){

        this.subcategoryRepository = subcategoryRepository;
        // this.productService = productService;
        // this.categoryService = categoryService;
        this.subcategoryBusinessRules = subcategoryBusinessRules;
        this.productBusinessRules = productBusinessRules;
        this.categoryBusinessRules = categoryBusinessRules;
    }

    @Override
    public Optional<Subcategory> findById(Integer id) {
        return subcategoryRepository.findById(id);
    }

    @Override
    public Optional<SubcategoryListingDto> findCategoryById(Integer id)
    {
        Subcategory subcategory = subcategoryBusinessRules.subcategoryMustExist(id);

        SubcategoryListingDto subcategoryListingDto = new SubcategoryListingDto();
        subcategoryListingDto.setId(subcategory.getId());
        subcategoryListingDto.setName(subcategoryListingDto.getName());

        return Optional.of(subcategoryListingDto);
    }

    @Override
    public void add(CreateSubcategoryDto createSubcategoryDto) {

        Optional<Subcategory> existingSubcategory = subcategoryRepository.findByName(createSubcategoryDto.getName());

        existingSubcategory.ifPresentOrElse(subcategory -> {
            if (!subcategory.getIsActive()) {
                // Alt kategori pasifse, aktif hale getir
                subcategory.setIsActive(true);
                subcategoryRepository.save(subcategory);
            } else {
                // Alt kategori zaten aktifse hata fırlat
                throw new RuntimeException("Subcategory with the same name already exists!");
            }
        }, () -> {
            // Yeni bir alt kategori oluştur
            Subcategory subcategory = new Subcategory();
            subcategory.setName(createSubcategoryDto.getName());
            subcategory.setIsActive(true); // Varsayılan olarak aktif yap
            Category category = categoryBusinessRules.categoryMustExist(createSubcategoryDto.getCategoryId());
            subcategory.setCategory(category);

            subcategoryRepository.save(subcategory);
        });
    }

    @Transactional
    public void delete(DeleteSubcategoryDto deleteSubcategoryDto){
        // id'ye göre kategori var mı iş kuralı
        Subcategory subcategory = subcategoryBusinessRules.subcategoryMustExist(deleteSubcategoryDto.getId());
        /*
        *Subcategory subcategory = this
                .findById(deleteSubcategoryDto.getId())
                .orElseThrow(() -> new RuntimeException("There is no subcategory for this id."));
        */

        // ürün kontrolü iş kuralı
        productBusinessRules.checkProductsExistInCategory(deleteSubcategoryDto.getId());

        /*
        *if(productService.hasProductsInCategory(deleteSubcategoryDto.getId()))
            throw new RuntimeException("Category cannot be deleted as it has associated products!");
        */
        subcategory.setIsActive(false);
        subcategoryRepository.save(subcategory);
    }


    @Override
    public List<SubcategoryListingDto> getAll() {
        List<Subcategory> subcategories = subcategoryRepository.findByIsActiveEquals( true);

        // Subcategory'leri DTO'ya dönüştür
        List<SubcategoryListingDto> subcategoryListingDtos = subcategories.stream()
                .map(subcategory -> new SubcategoryListingDto(
                        subcategory.getId(),
                        subcategory.getName()))
                .collect(Collectors.toList());

        return subcategoryListingDtos;
    }

    @Override
    public List<SubcategoryListingDto> listingSubcategoriesByCategoryId(Integer categoryId) {
        List<Subcategory> subcategories = subcategoryRepository.findByCategoryIdAndIsActiveEquals(categoryId, true);

        // Subcategory'leri DTO'ya dönüştür
        List<SubcategoryListingDto> subcategoryListingDtos = subcategories.stream()
                .map(subcategory -> new SubcategoryListingDto(
                        subcategory.getId(),
                        subcategory.getName()))
                .collect(Collectors.toList());

        return subcategoryListingDtos;
    }


}
