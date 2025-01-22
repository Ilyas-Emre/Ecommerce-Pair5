package org.turkcell.ecommercepair5.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.turkcell.ecommercepair5.dto.category.CategoryListingDto;
import org.turkcell.ecommercepair5.dto.category.CreateCategoryDto;
import org.turkcell.ecommercepair5.dto.category.DeleteCategoryDto;
import org.turkcell.ecommercepair5.service.CategoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("{id}")
    public Optional<CategoryListingDto> getCategoryById(@PathVariable("id") Integer id){
        return this.categoryService.findCategoryById(id);
    }

    // Tüm kategorileri almak
    @GetMapping
    public List<CategoryListingDto> getAllCategories(){
        return this.categoryService.getAll();
    }

    @PostMapping("/create")
    public void add(@RequestBody @Valid CreateCategoryDto createCategoryDto){
        categoryService.add(createCategoryDto);
    }

    @PutMapping("/delete/{id}")
    public void delete(@PathVariable Integer id){
        DeleteCategoryDto deleteCategoryDto = new DeleteCategoryDto();
        deleteCategoryDto.setId(id);

        categoryService.delete(deleteCategoryDto);
    }

}
