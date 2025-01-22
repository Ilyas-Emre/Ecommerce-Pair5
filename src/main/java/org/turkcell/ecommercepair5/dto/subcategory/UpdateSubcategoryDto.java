package org.turkcell.ecommercepair5.dto.subcategory;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

public class UpdateSubcategoryDto {
    @Max(value = 100, message = "Category name length should be less than 100 char.")
    @UniqueElements(message = "Category name should be unique")
    private String name;

    @NotNull(message = "Category Id cannot be null")
    private Integer categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
