package org.turkcell.ecommercepair5.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.turkcell.ecommercepair5.dto.subcategory.CreateSubcategoryDto;
import org.turkcell.ecommercepair5.dto.subcategory.DeleteSubcategoryDto;
import org.turkcell.ecommercepair5.dto.subcategory.SubcategoryListingDto;
import org.turkcell.ecommercepair5.service.SubcategoryService;

import java.util.List;
import java.util.Optional;


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

    @GetMapping("{id}")
    public Optional<SubcategoryListingDto> findById(@PathVariable("id") Integer id){
        return this.subcategoryService.findCategoryById(id);
    }

    // Belirli bir kategoriye ait alt kategorileri listelemek
    @GetMapping("category/{id}")
    public List<SubcategoryListingDto> getAllSubcategoriesByCategoryId(@PathVariable("id")Integer id){
        return this.subcategoryService.listingSubcategoriesByCategoryId(id);
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
