package org.turkcell.ecommercepair5.dto.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateCategoryDto {

    @NotBlank(message = "Category Name cannot be blank")
    @Size(max = 100, message = "Category name length should be less than 100 char.")
    private String name;

    private List<CreateCategoryDto> subCategories;
    private Boolean isActive;

    @JsonProperty("parent_category_id")
    private Integer parentId;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CreateCategoryDto> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CreateCategoryDto> subCategories) {
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
