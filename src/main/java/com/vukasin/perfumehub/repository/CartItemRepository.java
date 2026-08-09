package com.vukasin.perfumehub.repository;

import com.vukasin.perfumehub.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartId(Long cartId);

    Optional<CartItem> findByCartIdAndProductVariantId(Long cartId, Long productVariantId);

    boolean existsByCartIdAndProductVariantId(Long cartId, Long productVariantId);
}
