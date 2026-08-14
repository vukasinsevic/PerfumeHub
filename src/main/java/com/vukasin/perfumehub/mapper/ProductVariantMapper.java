package com.vukasin.perfumehub.mapper;

import com.vukasin.perfumehub.dto.response.ProductVariantResponse;
import com.vukasin.perfumehub.entity.ProductVariant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductVariantMapper {

    public ProductVariantResponse toResponse(ProductVariant productVariant) {

        return new ProductVariantResponse(
                productVariant.getId(),
                productVariant.getVolumeMl(),
                productVariant.getPrice(),
                productVariant.getStock(),
                productVariant.isActive(),
                productVariant.getPerfume().getId(),
                productVariant.getPerfume().getName()
        );

    }

    public List<ProductVariantResponse> toResponseList(List<ProductVariant> productVariants) {
        return productVariants.stream()
                .map(this::toResponse)
                .toList();
    }

}
