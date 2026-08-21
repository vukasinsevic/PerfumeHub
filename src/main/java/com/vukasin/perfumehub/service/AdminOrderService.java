package com.vukasin.perfumehub.service;

import com.vukasin.perfumehub.dto.request.UpdateOrderStatusRequest;
import com.vukasin.perfumehub.dto.response.AdminOrderSummaryResponse;
import com.vukasin.perfumehub.dto.response.OrderDetailsResponse;
import com.vukasin.perfumehub.entity.CustomerOrder;
import com.vukasin.perfumehub.entity.OrderItem;
import com.vukasin.perfumehub.enums.OrderStatus;
import com.vukasin.perfumehub.exception.InvalidOrderStatusTransitionException;
import com.vukasin.perfumehub.exception.ResourceNotFoundException;
import com.vukasin.perfumehub.mapper.OrderMapper;
import com.vukasin.perfumehub.repository.CustomerOrderRepository;
import com.vukasin.perfumehub.repository.OrderItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminOrderService {

    private final CustomerOrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    public AdminOrderService(
            CustomerOrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            OrderMapper orderMapper
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderDetailsResponse updateOrderStatus(
            Long orderId,
            UpdateOrderStatusRequest request
    ) {

        CustomerOrder order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        validateStatusTransition(
                order.getStatus(),
                request.status()
        );

        order.setStatus(request.status());

        CustomerOrder savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        return orderMapper.toDetailsResponse(savedOrder, orderItems);
    }

    @Transactional(readOnly = true)
    public List<AdminOrderSummaryResponse> getOrders() {

        List<CustomerOrder> orders =
                orderRepository.findAllByOrderByCreatedAtDesc();

        return orderMapper.toAdminSummaryResponseList(orders);
    }

    private void validateStatusTransition(
            OrderStatus currentStatus,
            OrderStatus newStatus
    ) {

        boolean valid = switch (currentStatus) {

            case PENDING ->
                newStatus == OrderStatus.PROCESSING
                        || newStatus == OrderStatus.CANCELLED;

            case PROCESSING ->
                newStatus == OrderStatus.SHIPPED
                        || newStatus == OrderStatus.CANCELLED;

            case SHIPPED ->
                newStatus == OrderStatus.DELIVERED;

            case DELIVERED, CANCELLED -> false;
        };

        if (!valid) {
            throw new InvalidOrderStatusTransitionException(
                    "Cannot change order status from "
                            + currentStatus
                            + " to "
                            + newStatus
            );
        }

    }

}
