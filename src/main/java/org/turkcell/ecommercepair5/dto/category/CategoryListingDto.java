package org.turkcell.ecommercepair5.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListingDto {

    private Integer id;
    private String name;
    private List<CategoryListingDto> subcategories;

    //private List<ProductListingDto> products;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryListingDto> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<CategoryListingDto> subcategories) {
        this.subcategories = subcategories;
    }

    /*
    public List<ProductListingDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductListingDto> products) {
        this.products = products;
    }
    */
}
