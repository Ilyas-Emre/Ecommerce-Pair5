package org.turkcell.ecommercepair5.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.dto.product.CreateProductDto;
import org.turkcell.ecommercepair5.dto.product.DeleteProductDto;
import org.turkcell.ecommercepair5.dto.product.ProductListingDto;
import org.turkcell.ecommercepair5.dto.product.UpdateProductDto;
import org.turkcell.ecommercepair5.entity.Category;
import org.turkcell.ecommercepair5.entity.Product;
import org.turkcell.ecommercepair5.repository.ProductRepository;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    //private final SubcategoryService subcategoryService;

    @Override
    public void createProduct(CreateProductDto createProductDto) {

        Category category = categoryService
                .findById(createProductDto.getCategoryId())
                .orElse(null);
        /*
        *Subcategory subcategory = subcategoryService
                .findById(createProductDto.getSubcategoryId())
                .orElse(null);
         */

        Product productWithSameName = productRepository
                .findByName(createProductDto.getName())
                .orElse(null);

        if (productWithSameName != null)
            throw new BusinessException("Product already exists");


        Product product = new Product();
        product.setName(createProductDto.getName());
        product.setStock(createProductDto.getStock());
        product.setUnitPrice(createProductDto.getUnitPrice());
        product.setCategory(category);
        product.setImageUrl(createProductDto.getImageUrl());
        product.setDescription(createProductDto.getDescription());
        product.setIsActive(true);
        //product.setSubcategory(subcategory);

        productRepository.save(product);

    }

    @Override
    public void updateProduct(UpdateProductDto updateProductDto) {

        Category category = categoryService
                .findById(updateProductDto.getCategoryId())
                .orElse(null);
        /*
        *Subcategory subcategory = subcategoryService
                .findById(updateProductDto.getCategoryId())
                .orElse(null);
        */

        Product productToUpdate = productRepository.findById(updateProductDto.getId())
                .orElseThrow(() -> new BusinessException("Product not found."));
        productToUpdate.setName(updateProductDto.getName());
        productToUpdate.setDescription(updateProductDto.getDescription());
        productToUpdate.setUnitPrice(updateProductDto.getUnitPrice());
        productToUpdate.setStock(updateProductDto.getStock());
        productToUpdate.setCategory(category);
        //productToUpdate.setSubcategory(subcategory);
        productToUpdate.setImageUrl(updateProductDto.getImageUrl());
        productRepository.save(productToUpdate);
    }


    @Override
    public void deleteProduct(DeleteProductDto deleteProductDto) {
        List<Integer> idsToDelete = deleteProductDto.getId();

        for (Integer id : idsToDelete) {
            Product productToDelete = productRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Product not found with id: " + id));

            // Check if the product is linked to any order details
            if (!productToDelete.getOrderDetails().isEmpty()) {
                throw new BusinessException("Cannot delete product linked to order details!");
            }

            // Set the product as inactive instead of deleting it
            productToDelete.setIsActive(false);

            // Save the updated product
            productRepository.save(productToDelete);
        }
    }


    @Override
    public List<ProductListingDto> listProducts(String category, Double minPrice, Double maxPrice, Boolean inStock) {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .filter(product -> product.getIsActive() &&
                        (category == null || product.getCategory().getName().equalsIgnoreCase(category)) &&
                        (minPrice == null || product.getUnitPrice().doubleValue() >= minPrice) &&
                        (maxPrice == null || product.getUnitPrice().doubleValue() <= maxPrice) &&
                        (inStock == null || (inStock && product.getStock() > 0)))
                .map(product -> new ProductListingDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getUnitPrice(),
                        product.getStock(),
                        product.getCategory().getName(),
                        product.getCategory().getName(),
                        product.getImageUrl()
                ))
                .collect(Collectors.toList());

    }
    @Override
    public List<Product> getProductsByCategory(Integer categoryId) {
        return productRepository.findProductsByCategory(categoryId);
    }

    @Override
    public List<Product> findAllProductsOrderedByUnitPrice() {
        return productRepository.findAllProductsOrderedByUnitPrice();
    }

    @Override
    public List<Product> findAllProductsOrderedByUnitPriceDesc() {
        return productRepository.findAllProductsOrderedByUnitPriceDesc();
    }

    @Override
    public List<Product> findProductsInStock() {
        return productRepository.findProductsInStock();
    }

    @Override
    public boolean hasProductsInCategory(Integer categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }

    public Optional<Product> findById(Integer productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }
}