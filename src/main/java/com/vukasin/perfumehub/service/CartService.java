package com.vukasin.perfumehub.service;

import com.vukasin.perfumehub.dto.request.AddCartItemRequest;
import com.vukasin.perfumehub.dto.request.UpdateCartItemQuantityRequest;
import com.vukasin.perfumehub.dto.response.CartResponse;
import com.vukasin.perfumehub.entity.Cart;
import com.vukasin.perfumehub.entity.CartItem;
import com.vukasin.perfumehub.entity.ProductVariant;
import com.vukasin.perfumehub.exception.InsufficientStockException;
import com.vukasin.perfumehub.exception.InvalidRequestException;
import com.vukasin.perfumehub.exception.ResourceNotFoundException;
import com.vukasin.perfumehub.mapper.CartMapper;
import com.vukasin.perfumehub.repository.AppUserRepository;
import com.vukasin.perfumehub.repository.CartItemRepository;
import com.vukasin.perfumehub.repository.CartRepository;
import com.vukasin.perfumehub.repository.ProductVariantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final AppUserRepository userRepository;
    private final CartMapper cartMapper;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductVariantRepository productVariantRepository,
            AppUserRepository userRepository,
            CartMapper cartMapper
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productVariantRepository = productVariantRepository;
        this.userRepository = userRepository;
        this.cartMapper = cartMapper;
    }

    @Transactional(readOnly = true)
    public CartResponse getCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        return cartMapper.toResponse(cart, cartItems);
    }

    @Transactional
    public CartResponse addItemToCart(Long userId, AddCartItemRequest request) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        ProductVariant productVariant = productVariantRepository.findById(request.productVariantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product variant not found"));

        if (request.quantity() > productVariant.getStock()) {
            throw new InsufficientStockException("Not enough product in stock");
        }

        if (!productVariant.isActive()) {
            throw new InvalidRequestException("Product variant is not available");
        }

        CartItem item = cartItemRepository.findByCartIdAndProductVariantId(
                cart.getId(), productVariant.getId()
        ).orElse(null);

        if (item!=null) {

            int newQuantity = item.getQuantity() + request.quantity();

            if (newQuantity > productVariant.getStock()) {
                throw new InsufficientStockException("Not enough product in stock");
            }

            item.setQuantity(newQuantity);

            cartItemRepository.save(item);
        } else {

            CartItem newItem = new CartItem(
                    request.quantity(),
                    cart,
                    productVariant
            );

            cartItemRepository.save(newItem);
        }

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        return cartMapper.toResponse(cart, items);
    }

    @Transactional
    public CartResponse updateCartItemQuantity(
            Long userId,
            Long cartItemId,
            UpdateCartItemQuantityRequest request
    ) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new ResourceNotFoundException("Cart item not found");
        }

        if (request.quantity() > item.getProductVariant().getStock()) {
            throw new InsufficientStockException("Not enough product in stock");
        }

        item.setQuantity(request.quantity());
        cartItemRepository.save(item);

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        return cartMapper.toResponse(cart, items);
    }

    @Transactional
    public void removeCartItem(Long userId, Long cartItemId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new ResourceNotFoundException("Cart item not found");
        }

        cartItemRepository.delete(item);
    }

    @Transactional
    public void clearCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));


        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        cartItemRepository.deleteAll(items);
    }

}
