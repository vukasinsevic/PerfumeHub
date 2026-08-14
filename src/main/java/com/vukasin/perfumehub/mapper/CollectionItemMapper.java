package com.vukasin.perfumehub.mapper;

import com.vukasin.perfumehub.dto.response.CollectionItemResponse;
import com.vukasin.perfumehub.entity.CollectionItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollectionItemMapper {

    public CollectionItemResponse toResponse(CollectionItem collectionItem) {

        return new CollectionItemResponse(
                collectionItem.getId(),
                collectionItem.getAddedAt(),
                collectionItem.getStatus(),
                collectionItem.getPerfume().getId(),
                collectionItem.getPerfume().getName(),
                collectionItem.getPerfume().getBrand().getName(),
                collectionItem.getPerfume().getImageUrl()
        );

    }

    public List<CollectionItemResponse> toResponseList(List<CollectionItem> collectionItems) {
        return collectionItems.stream()
                .map(this::toResponse)
                .toList();
    }

}
