package com.vukasin.perfumehub.repository;

import com.vukasin.perfumehub.entity.ProductNotification;
import com.vukasin.perfumehub.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductNotificationRepository extends JpaRepository<ProductNotification, Long> {

    List<ProductNotification> findByUserId(Long userId);

    Optional<ProductNotification> findByUserIdAndProductVariantIdAndType(
            Long userId,
            Long productVariantId,
            NotificationType type
    );

    boolean existsByUserIdAndProductVariantIdAndType(
            Long userId,
            Long productVariantId,
            NotificationType type
    );

}
