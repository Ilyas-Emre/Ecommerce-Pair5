package org.turkcell.ecommercepair5.dto.category;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;
import org.turkcell.ecommercepair5.dto.subcategory.CreateSubcategoryDto;
import org.turkcell.ecommercepair5.dto.subcategory.SubcategoryListingDto;
import org.turkcell.ecommercepair5.entity.Product;
import org.turkcell.ecommercepair5.entity.Subcategory;

import java.util.List;

public class CreateCategoryDto {

    @NotBlank(message = "Category Name cannot be blank")
    @Size(max = 100, message = "Category name length should be less than 100 char.")
    private String name;

    private List<CreateSubcategoryDto> subCategories;
    private Boolean isActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CreateSubcategoryDto> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CreateSubcategoryDto> subCategories) {
        this.subCategories = subCategories;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    //private List<Product> products;
}
