package com.mohan.class_com.repository;

import com.mohan.class_com.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCart_IdAndProduct_Id(Long cartId, Long prodId);
    List<CartItem> findByCart_Id(Long id);
    Optional<CartItem> findByIdAndCart_Id(Long id, Long cartId);
    List<CartItem> findAllByCart_Id(Long cartId);
}
