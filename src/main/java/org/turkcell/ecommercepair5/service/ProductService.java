package org.turkcell.ecommercepair5.service;


import org.turkcell.ecommercepair5.dto.product.CreateProductDto;
import org.turkcell.ecommercepair5.dto.product.DeleteProductDto;
import org.turkcell.ecommercepair5.dto.product.ProductListingDto;
import org.turkcell.ecommercepair5.dto.product.UpdateProductDto;
import org.turkcell.ecommercepair5.dto.user.DeleteUserDto;
import org.turkcell.ecommercepair5.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void createProduct(CreateProductDto createProductDto);
    void updateProduct(UpdateProductDto updateProductDto);
    void deleteProduct(DeleteProductDto deleteProductDto);
    List<ProductListingDto> listProducts(String category, Double minPrice, Double maxPrice, Boolean inStock);
    List<Product> getProductsByCategory(Integer categoryId);

    List<Product> findAllProductsOrderedByUnitPrice();

    List<Product> findAllProductsOrderedByUnitPriceDesc();

    List<Product> findProductsInStock();

    boolean hasProductsInCategory(Integer categoryId);
    Optional<Product> findById(Integer productId);

}
