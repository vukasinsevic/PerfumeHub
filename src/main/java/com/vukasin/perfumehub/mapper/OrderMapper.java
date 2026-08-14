package com.vukasin.perfumehub.mapper;

import com.vukasin.perfumehub.dto.response.OrderDetailsResponse;
import com.vukasin.perfumehub.dto.response.OrderItemResponse;
import com.vukasin.perfumehub.dto.response.OrderSummaryResponse;
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

}
