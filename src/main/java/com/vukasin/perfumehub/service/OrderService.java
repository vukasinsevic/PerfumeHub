package com.vukasin.perfumehub.service;

import com.vukasin.perfumehub.dto.response.OrderDetailsResponse;
import com.vukasin.perfumehub.dto.response.OrderSummaryResponse;
import com.vukasin.perfumehub.entity.*;
import com.vukasin.perfumehub.exception.InsufficientStockException;
import com.vukasin.perfumehub.exception.InvalidRequestException;
import com.vukasin.perfumehub.exception.ResourceNotFoundException;
import com.vukasin.perfumehub.mapper.OrderMapper;
import com.vukasin.perfumehub.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final CustomerOrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final AppUserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;

    public OrderService(
            CustomerOrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ProductVariantRepository productVariantRepository,
            AppUserRepository userRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            OrderMapper orderMapper
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productVariantRepository = productVariantRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional(readOnly = true)
    public List<OrderSummaryResponse> getOrders(Long userId) {

        List<CustomerOrder> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);

        return orderMapper.toSummaryResponseList(orders);
    }

    @Transactional
    public OrderDetailsResponse createOrder(Long userId, String shippingAddress) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new InvalidRequestException("Cart is empty");
        }

        AppUser user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItem cartItem: cartItems) {

            ProductVariant productVariant = cartItem.getProductVariant();

            if (cartItem.getQuantity() > productVariant.getStock()) {
                throw new InsufficientStockException(
                        "Not enough stock for " + productVariant.getPerfume().getName());
            }

            BigDecimal subtotal = productVariant.getPrice().multiply(
                    BigDecimal.valueOf(cartItem.getQuantity()));

            totalPrice = totalPrice.add(subtotal);
        }

        CustomerOrder order = new CustomerOrder(
                totalPrice,
                shippingAddress,
                user
        );

        CustomerOrder savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {

            ProductVariant productVariant = cartItem.getProductVariant();

            OrderItem orderItem = new OrderItem(
                    cartItem.getQuantity(),
                    productVariant.getPrice(),
                    savedOrder,
                    productVariant
            );

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);

            orderItems.add(savedOrderItem);

            productVariant.setStock(
                    productVariant.getStock() - cartItem.getQuantity()
            );

            productVariantRepository.save(productVariant);
        }

        for (CartItem cartItem : cartItems) {
            cartItemRepository.delete(cartItem);
        }

        return orderMapper.toDetailsResponse(
                savedOrder,
                orderItems
        );
    }

    @Transactional(readOnly = true)
    public OrderDetailsResponse getOrderDetails(Long userId, Long orderId) {

        CustomerOrder order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        return orderMapper.toDetailsResponse(order, orderItems);
    }

}
