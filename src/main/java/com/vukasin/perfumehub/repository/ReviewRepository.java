package com.vukasin.perfumehub.repository;

import com.vukasin.perfumehub.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByPerfumeIdOrderByCreatedAtDesc(Long perfumeId);

    Optional<Review> findByUserIdAndPerfumeId(Long userId, Long perfumeId);

    boolean existsByUserIdAndPerfumeId(Long userId, Long perfumeId);

}
