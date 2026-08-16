package com.vukasin.perfumehub.repository;

import com.vukasin.perfumehub.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    List<ProductVariant> findByPerfumeId(Long perfumeId);

    List<ProductVariant> findByPerfumeIdAndActiveTrue(Long perfumeId);

    boolean existsByVolumeMlAndPerfumeId(Integer volumeMl, Long perfumeId);

    boolean existsByVolumeMlAndPerfumeIdAndIdNot(
            Integer volumeMl,
            Long perfumeId,
            Long id
    );
}
