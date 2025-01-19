package org.turkcell.ecommercepair5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.turkcell.ecommercepair5.entity.CartDetail;
import org.turkcell.ecommercepair5.entity.CartDetailId;

public interface CartDetailRepository extends JpaRepository<CartDetail, CartDetailId> {



}
