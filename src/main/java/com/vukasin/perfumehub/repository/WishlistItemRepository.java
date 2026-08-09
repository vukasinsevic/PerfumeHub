package com.vukasin.perfumehub.repository;

import com.vukasin.perfumehub.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByUserId(Long userId);

    Optional<WishlistItem> findByUserIdAndPerfumeId(Long userId, Long perfumeId);

    boolean existsByUserIdAndPerfumeId(Long userId, Long perfumeId);
}
