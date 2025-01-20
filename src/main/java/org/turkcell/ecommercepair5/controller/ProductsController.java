package org.turkcell.ecommercepair5.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.turkcell.ecommercepair5.dto.product.CreateProductDto;
import org.turkcell.ecommercepair5.dto.product.ProductListingDto;
import org.turkcell.ecommercepair5.dto.product.UpdateProductDto;
import org.turkcell.ecommercepair5.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductDto createProductDto) {
        productService.createProduct(createProductDto);
        return ResponseEntity.ok("Product created successfully.");
    }

    @PutMapping
    public ResponseEntity<String> updateProduct(@RequestBody UpdateProductDto updateProductDto) {
        productService.updateProduct(updateProductDto);
        return ResponseEntity.ok("Product updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");
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
}
