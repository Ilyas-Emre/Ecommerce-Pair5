package org.turkcell.ecommercepair5.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turkcell.ecommercepair5.dto.subcategory.CreateSubcategoryDto;
import org.turkcell.ecommercepair5.dto.subcategory.DeleteSubcategoryDto;
import org.turkcell.ecommercepair5.dto.subcategory.SubcategoryListingDto;
import org.turkcell.ecommercepair5.entity.Category;
import org.turkcell.ecommercepair5.entity.Subcategory;
import org.turkcell.ecommercepair5.repository.CategoryRepository;
import org.turkcell.ecommercepair5.repository.SubcategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final ProductService productService;
    private final CategoryService categoryService;

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, @Lazy ProductService productService, @Lazy CategoryService categoryService){

        this.subcategoryRepository = subcategoryRepository;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public Optional<Subcategory> findById(Integer id)
    {
        return subcategoryRepository.findById(id);
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
            Category category = categoryService.findById(createSubcategoryDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found!"));
            subcategory.setCategory(category);

            subcategoryRepository.save(subcategory);
        });
    }

    @Transactional
    public void delete(DeleteSubcategoryDto deleteSubcategoryDto){

        Subcategory subcategory = this
                .findById(deleteSubcategoryDto.getId())
                .orElseThrow(() -> new RuntimeException("There is no subcategory for this id."));

        if(productService.hasProductsInCategory(deleteSubcategoryDto.getId()))
            throw new RuntimeException("Category cannot be deleted as it has associated products!");

        subcategory.setIsActive(false);
        subcategoryRepository.save(subcategory);
    }

    @Override
    public List<SubcategoryListingDto> getAll() {
        List<SubcategoryListingDto> subcategoryListingDtos = subcategoryRepository
                .findAll()
                .stream()
                .map(subcategory -> new SubcategoryListingDto(subcategory.getId(), subcategory.getName()))
                .toList();
        return subcategoryListingDtos;
    }

    @Override
    public List<SubcategoryListingDto> listingSubcategoriesByCategoryId(Integer categoryId) {
        List<Subcategory> subcategories = subcategoryRepository.findByCategoryIdAndIsActive(categoryId, true);

        // Subcategory'leri DTO'ya dönüştür
        List<SubcategoryListingDto> subcategoryListingDtos = subcategories.stream()
                .map(subcategory -> new SubcategoryListingDto(
                        subcategory.getId(),
                        subcategory.getName()))
                .collect(Collectors.toList());

        return subcategoryListingDtos;
    }


}
