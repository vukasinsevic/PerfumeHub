package com.vukasin.perfumehub.mapper;

import com.vukasin.perfumehub.dto.response.WishlistItemResponse;
import com.vukasin.perfumehub.entity.WishlistItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WishlistItemMapper {

    public WishlistItemResponse toResponse(WishlistItem wishlistItem) {

        return new WishlistItemResponse(
                wishlistItem.getId(),
                wishlistItem.getAddedAt(),
                wishlistItem.getPerfume().getId(),
                wishlistItem.getPerfume().getName(),
                wishlistItem.getPerfume().getBrand().getName(),
                wishlistItem.getPerfume().getImageUrl()
        );

    }

    public List<WishlistItemResponse> toResponseList(List<WishlistItem> wishlistItems) {
        return wishlistItems.stream()
                .map(this::toResponse)
                .toList();
    }

}
