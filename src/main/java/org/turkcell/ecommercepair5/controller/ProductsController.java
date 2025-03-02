package org.turkcell.ecommercepair5.controller;



import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.turkcell.ecommercepair5.dto.cartdetail.CartDetailDto;
import org.turkcell.ecommercepair5.dto.product.CreateProductDto;
import org.turkcell.ecommercepair5.dto.product.DeleteProductDto;
import org.turkcell.ecommercepair5.dto.product.ProductListingDto;
import org.turkcell.ecommercepair5.dto.product.UpdateProductDto;
import org.turkcell.ecommercepair5.dto.user.DeleteUserDto;
import org.turkcell.ecommercepair5.dto.user.UserListingDto;
import org.turkcell.ecommercepair5.entity.Product;
import org.turkcell.ecommercepair5.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {


    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody @Valid CreateProductDto createProductDto) {
        productService.createProduct(createProductDto);
        return ResponseEntity.ok("Product created successfully.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody @Valid UpdateProductDto updateProductDto) {
        productService.updateProduct(updateProductDto);
        return ResponseEntity.ok("Product updated successfully.");
    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }*/

    @PutMapping("/delete")
    public void delete(@RequestBody DeleteProductDto deleteProductDto)

    {
        productService.deleteProduct(deleteProductDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductListingDto>> listProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean inStock) {
        List<ProductListingDto> products = productService.listProducts(category, minPrice, maxPrice, inStock);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable Integer categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @GetMapping("/asc")
    public List<Product> getAllProductsOrderedByUnitPrice() {
        return productService.findAllProductsOrderedByUnitPrice();
    }

    @GetMapping("/desc")
    public List<Product> getAllProductsOrderedByUnitPriceDesc() {
        return productService.findAllProductsOrderedByUnitPriceDesc();
    }

    @GetMapping("/instock")
    public List<Product> getProductsInStock() {
        return productService.findProductsInStock();
    }

}
