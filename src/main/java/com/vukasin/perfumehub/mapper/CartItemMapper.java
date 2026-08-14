package com.vukasin.perfumehub.mapper;

import com.vukasin.perfumehub.dto.response.CartItemResponse;
import com.vukasin.perfumehub.entity.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartItemMapper {

    public CartItemResponse toResponse(CartItem cartItem) {

        BigDecimal subtotal = cartItem.getProductVariant().getPrice().multiply(
                BigDecimal.valueOf(cartItem.getQuantity())
        );

        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getProductVariant().getId(),
                cartItem.getProductVariant().getPerfume().getId(),
                cartItem.getProductVariant().getPerfume().getName(),
                cartItem.getProductVariant().getPerfume().getBrand().getName(),
                cartItem.getProductVariant().getPerfume().getImageUrl(),
                cartItem.getProductVariant().getVolumeMl(),
                cartItem.getProductVariant().getPrice(),
                cartItem.getQuantity(),
                subtotal
        );

    }

    public List<CartItemResponse> toResponseList(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::toResponse)
                .toList();
    }

}
