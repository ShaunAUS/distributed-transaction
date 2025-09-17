package com.example.monolithic.controller;

import com.example.monolithic.controller.dto.CreateOrderRequest;
import com.example.monolithic.controller.dto.PlaceOrderRequest;
import com.example.monolithic.order.application.OrderService;
import com.example.monolithic.order.application.dto.CreateOrderResponse;
import com.example.monolithic.order.application.dto.CreateOrderResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        CreateOrderResult result = orderService.createOrder(createOrderRequest.toCreateOrderCommand());
        return new CreateOrderResponse(result.orderId());

    }

    @PostMapping("/order/place")
    public void placeOrder(
            @RequestBody PlaceOrderRequest request
    ) throws InterruptedException {
        orderService.placeOrder(request.toPlaceOrderCommand());
    }
}