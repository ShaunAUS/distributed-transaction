package com.example.monolithic.controller;

import com.example.monolithic.controller.dto.PlaceOrderRequest;
import com.example.monolithic.order.application.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/place")
    public void placeOrder(
            @RequestBody PlaceOrderRequest request
    ) {
        orderService.placeOrder(request.toPlaceOrderCommand());
    }
}