package org.turkcell.ecommercepair5.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.turkcell.ecommercepair5.dto.category.CategoryListingDto;
import org.turkcell.ecommercepair5.dto.category.CreateCategoryDto;
import org.turkcell.ecommercepair5.dto.category.DeleteCategoryDto;
import org.turkcell.ecommercepair5.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryListingDto> getAll(){
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
