package org.turkcell.ecommercepair5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.turkcell.ecommercepair5.entity.CartDetail;
import org.turkcell.ecommercepair5.entity.CartDetailId;

import java.util.List;
import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetail, CartDetailId> {


    Optional<CartDetail> findByCartIdAndProductId(Integer cartId, Integer productId);

    List<CartDetail> findByCartId(Integer cartId);
}
