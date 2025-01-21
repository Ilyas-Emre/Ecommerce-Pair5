package org.turkcell.ecommercepair5.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.turkcell.ecommercepair5.dto.product.ProductListingDto;
import org.turkcell.ecommercepair5.dto.subcategory.SubcategoryListingDto;
import org.turkcell.ecommercepair5.entity.Subcategory;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListingDto {

    private Integer id;
    private String name;
    private List<SubcategoryListingDto> subcategories;
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

    public List<SubcategoryListingDto> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryListingDto> subcategories) {
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
