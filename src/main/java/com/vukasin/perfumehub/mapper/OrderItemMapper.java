package com.vukasin.perfumehub.mapper;

import com.vukasin.perfumehub.dto.response.OrderItemResponse;
import com.vukasin.perfumehub.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class OrderItemMapper {

    public OrderItemResponse toResponse(OrderItem orderItem) {

        BigDecimal subtotal = orderItem.getPricePerItem().multiply(
                BigDecimal.valueOf(orderItem.getQuantity())
        );

        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getProductVariant().getId(),
                orderItem.getProductVariant().getPerfume().getId(),
                orderItem.getProductVariant().getPerfume().getName(),
                orderItem.getProductVariant().getPerfume().getBrand().getName(),
                orderItem.getProductVariant().getPerfume().getImageUrl(),
                orderItem.getProductVariant().getVolumeMl(),
                orderItem.getPricePerItem(),
                orderItem.getQuantity(),
                subtotal
        );

    }

    public List<OrderItemResponse> toResponseList(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::toResponse)
                .toList();
    }

}
