package com.vukasin.perfumehub.mapper;

import com.vukasin.perfumehub.dto.response.ProductNotificationResponse;
import com.vukasin.perfumehub.entity.ProductNotification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductNotificationMapper {

    public ProductNotificationResponse toResponse(ProductNotification productNotification) {

        return new ProductNotificationResponse(
                productNotification.getId(),
                productNotification.getType(),
                productNotification.isActive(),
                productNotification.getCreatedAt(),
                productNotification.getLastKnownPrice(),
                productNotification.getProductVariant().getId(),
                productNotification.getProductVariant().getVolumeMl(),
                productNotification.getProductVariant().getPrice(),
                productNotification.getProductVariant().getPerfume().getId(),
                productNotification.getProductVariant().getPerfume().getName(),
                productNotification.getProductVariant().getPerfume().getBrand().getName(),
                productNotification.getProductVariant().getPerfume().getImageUrl()
        );

    }

    public List<ProductNotificationResponse> toResponseList(
            List<ProductNotification> productNotifications
    ) {
        return productNotifications.stream()
                .map(this::toResponse)
                .toList();
    }

}
