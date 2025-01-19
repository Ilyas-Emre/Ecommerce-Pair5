package org.turkcell.ecommercepair5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.turkcell.ecommercepair5.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {



}
