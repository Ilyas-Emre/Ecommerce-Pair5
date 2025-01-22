package org.turkcell.ecommercepair5.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.turkcell.ecommercepair5.dto.category.CategoryListingDto;
import org.turkcell.ecommercepair5.dto.category.CreateCategoryDto;
import org.turkcell.ecommercepair5.dto.subcategory.CreateSubcategoryDto;
import org.turkcell.ecommercepair5.dto.subcategory.DeleteSubcategoryDto;
import org.turkcell.ecommercepair5.dto.subcategory.SubcategoryListingDto;
import org.turkcell.ecommercepair5.repository.SubcategoryRepository;
import org.turkcell.ecommercepair5.service.SubcategoryService;

import java.util.List;


@RestController
@RequestMapping("subcategories")
public class SubcategoriesController {
    private final SubcategoryService subcategoryService;

    public SubcategoriesController(SubcategoryService subcategoryService){
        this.subcategoryService = subcategoryService;
    }

    @GetMapping
    public List<SubcategoryListingDto> getAll(){
        return this.subcategoryService.getAll();
    }

    @PostMapping("create")
    public void add(@RequestBody @Valid CreateSubcategoryDto createSubcategoryDto){
        subcategoryService.add(createSubcategoryDto);
    }

    @PutMapping("{id}")
    public void delete(@PathVariable("id") Integer id ){
        DeleteSubcategoryDto deleteSubcategoryDto = new DeleteSubcategoryDto();
        deleteSubcategoryDto.setId(id);

        subcategoryService.delete(deleteSubcategoryDto);
    }

}
