package org.turkcell.ecommercepair5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.turkcell.ecommercepair5.entity.Product;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    boolean existsByName(String name);

    Optional<Product> findByName(String name);

    boolean existsByCategoryId(Integer categoryId);

    // 2- JPQL -> SQL + JPA
//    @Query("SELECT p FROM Product p " +
//            "JOIN p.category c " +
//            "LEFT JOIN c.subcategories sc " +
//            "WHERE (:categoryId IS NULL OR c.id = :categoryId) " +
//            "AND (:subcategoryId IS NULL OR sc.id = :subcategoryId) " +
//            "AND (:minPrice IS NULL OR p.unitPrice >= :minPrice) " +
//            "AND (:maxPrice IS NULL OR p.unitPrice <= :maxPrice) " +
//            "AND (:inStock IS NULL OR p.stock > 0)")
//    List<Product> findProductsByFilters(@Param("categoryId") Integer categoryId,
//                                        @Param("subcategoryId") Integer subcategoryId,
//                                        @Param("minPrice") BigDecimal minPrice,
//                                        @Param("maxPrice") BigDecimal maxPrice,
//                                        @Param("inStock") Boolean inStock);    //@Query("Select p from Product p where p.name = ?1 and p.unitPrice= ?2", nativeQuery = false)



    @Query("SELECT p FROM Product p " +
            "JOIN p.category c " +
            "WHERE c.id = :categoryId")
    List<Product> findProductsByCategory(@Param("categoryId") Integer categoryId);

    @Query("SELECT p FROM Product p ORDER BY p.unitPrice ASC")
    List<Product> findAllProductsOrderedByUnitPrice();

    @Query("SELECT p FROM Product p ORDER BY p.unitPrice DESC")
    List<Product> findAllProductsOrderedByUnitPriceDesc();

    @Query("SELECT p FROM Product p " +
            "WHERE p.stock > 0")
    List<Product> findProductsInStock();


}

