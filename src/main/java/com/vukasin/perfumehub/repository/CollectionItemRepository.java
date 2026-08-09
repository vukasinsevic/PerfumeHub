package com.vukasin.perfumehub.repository;

import com.vukasin.perfumehub.entity.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {

    List<CollectionItem> findByUserId(Long userId);

    Optional<CollectionItem> findByUserIdAndPerfumeId(Long userId, Long perfumeId);

    boolean existsByUserIdAndPerfumeId(Long userId, Long perfumeId);
}
