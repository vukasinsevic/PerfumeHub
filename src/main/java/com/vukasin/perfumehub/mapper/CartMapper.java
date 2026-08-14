package com.vukasin.perfumehub.mapper;

import com.vukasin.perfumehub.dto.response.CartItemResponse;
import com.vukasin.perfumehub.dto.response.CartResponse;
import com.vukasin.perfumehub.entity.Cart;
import com.vukasin.perfumehub.entity.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartMapper {

    private final CartItemMapper cartItemMapper;

    public CartMapper(CartItemMapper cartItemMapper) {
        this.cartItemMapper = cartItemMapper;
    }

    public CartResponse toResponse(Cart cart, List<CartItem> cartItems) {

        List<CartItemResponse> items = cartItemMapper.toResponseList(cartItems);

        BigDecimal totalPrice = items.stream()
                .map(CartItemResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(
                cart.getId(),
                items,
                totalPrice
        );

    }

}
