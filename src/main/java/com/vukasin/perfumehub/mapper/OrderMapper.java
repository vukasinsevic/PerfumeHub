package com.vukasin.perfumehub.mapper;

import com.vukasin.perfumehub.dto.response.*;
import com.vukasin.perfumehub.entity.AppUser;
import com.vukasin.perfumehub.entity.CustomerOrder;
import com.vukasin.perfumehub.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    public OrderDetailsResponse toDetailsResponse(CustomerOrder order, List<OrderItem> orderItems) {

        List<OrderItemResponse> items = orderItemMapper.toResponseList(orderItems);

        return new OrderDetailsResponse(
                order.getId(),
                order.getCreatedAt(),
                order.getStatus(),
                order.getTotalPrice(),
                order.getShippingAddress(),
                items
        );

    }

    public OrderSummaryResponse toSummaryResponse(CustomerOrder order) {

        return new OrderSummaryResponse(
                order.getId(),
                order.getCreatedAt(),
                order.getStatus(),
                order.getTotalPrice()
        );

    }

    public List<OrderSummaryResponse> toSummaryResponseList(
            List<CustomerOrder> orders
    ) {
        return orders.stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    public AdminOrderSummaryResponse toAdminSummaryResponse(
            CustomerOrder order
    ) {

        AppUser user = order.getUser();

        OrderCustomerResponse customer =
                new OrderCustomerResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                );

        return new AdminOrderSummaryResponse(
                order.getId(),
                order.getCreatedAt(),
                order.getStatus(),
                order.getTotalPrice(),
                customer
        );
    }

    public List<AdminOrderSummaryResponse> toAdminSummaryResponseList(
            List<CustomerOrder> orders
    ) {
        return orders.stream()
                .map(this::toAdminSummaryResponse)
                .toList();
    }

}
