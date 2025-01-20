package org.turkcell.ecommercepair5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.turkcell.ecommercepair5.entity.Product;

<<<<<<< HEAD
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    boolean existsByName(String name);
    Optional<Product> findByName(String name);
=======
public interface ProductRepository extends JpaRepository<Product, Long> {

>>>>>>> 795569e52179b38f71c23b3864024f09d2056165


}
