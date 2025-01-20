package org.turkcell.ecommercepair5.service;

import org.turkcell.ecommercepair5.dto.product.CreateProductDto;
import org.turkcell.ecommercepair5.dto.product.DeleteProductDto;
import org.turkcell.ecommercepair5.dto.product.ProductListingDto;
import org.turkcell.ecommercepair5.dto.product.UpdateProductDto;

import java.util.List;

public interface ProductService {

    void createProduct(CreateProductDto createProductDto);
    void updateProduct(UpdateProductDto updateProductDto);
    void deleteProduct(Integer id);
    List<ProductListingDto> listProducts(String category, Double minPrice, Double maxPrice, Boolean inStock);




}
