package org.turkcell.ecommercepair5.service;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.dto.product.CreateProductDto;
import org.turkcell.ecommercepair5.dto.product.ProductListingDto;
import org.turkcell.ecommercepair5.dto.product.UpdateProductDto;
import org.turkcell.ecommercepair5.entity.Category;
import org.turkcell.ecommercepair5.entity.Product;
import org.turkcell.ecommercepair5.entity.Subcategory;
import org.turkcell.ecommercepair5.repository.CategoryRepository;
import org.turkcell.ecommercepair5.repository.ProductRepository;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    @Override
    public void createProduct(CreateProductDto createProductDto) {

        Category category = categoryService
                .findById(createProductDto.getCategoryId())
                .orElse(null);

        Product productWithSameName = productRepository
                .findByName(createProductDto.getName())
                .orElse(null);

        if(productWithSameName != null)
            throw new BusinessException("Product already exists");


        Product product = new Product();
        product.setName(createProductDto.getName());
        product.setStock(createProductDto.getStock());
        product.setUnitPrice(createProductDto.getUnitPrice());
        product.setCategory(category);

        productRepository.save(product);

    }
    @Override
    public void updateProduct(UpdateProductDto updateProductDto) {

        Category category = categoryService
                .findById(updateProductDto.getCategoryId())
                .orElse(null);

        Subcategory subcategory = subcategoryService
                .findById(updateProductDto.getCategoryId())
                .orElse(null);

        Product productToUpdate = productRepository.findById(updateProductDto.getId())
                .orElseThrow(() -> new BusinessException("Product not found."));
        productToUpdate.setName(updateProductDto.getName());
        productToUpdate.setDescription(updateProductDto.getDescription());
        productToUpdate.setUnitPrice(updateProductDto.getUnitPrice());
        productToUpdate.setStock(updateProductDto.getStock());
        productToUpdate.setCategory(category);
        productToUpdate.setSubcategory(subcategory);
        productRepository.save(productToUpdate);
    }

    @Override
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Product not found!"));

        if (!product.getOrderDetails().isEmpty()) {
            throw new BusinessException("Cannot delete product linked to orders!");
        }

        productRepository.delete(product);
    }

    @Override
    public List<ProductListingDto> listProducts(String category, Double minPrice, Double maxPrice, Boolean inStock) {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .filter(product -> (category == null || product.getCategory().getName().equalsIgnoreCase(category)) &&
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
                        product.getImageUrl()
                ))
                .collect(Collectors.toList());

    }
=======
public class ProductServiceImpl {
>>>>>>> 795569e52179b38f71c23b3864024f09d2056165
}
