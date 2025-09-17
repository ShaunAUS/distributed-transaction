package com.example.monolithic.controller.dto;

import com.example.monolithic.order.application.dto.CreateOrderCommand;
import com.example.monolithic.order.domain.OrderItem;

import java.util.List;

public record CreateOrderRequest(
        List<OrderItem> orderItems
) {

    public CreateOrderCommand toCreateOrderCommand() {
        return new CreateOrderCommand(
                orderItems.stream()
                        .map(item -> new CreateOrderCommand.OrderItem(
                                item.productId(),
                                item.quantity()
                        ))
                        .toList()
        );
    }

    public record OrderItem(
            Long productId,
            Long quantity
    ) {
    }
}
