package com.example.monolithic.controller;

import com.example.monolithic.controller.dto.CreateOrderRequest;
import com.example.monolithic.controller.dto.PlaceOrderRequest;
import com.example.monolithic.order.application.OrderService;
import com.example.monolithic.order.application.RedisLockService;
import com.example.monolithic.order.application.dto.CreateOrderResponse;
import com.example.monolithic.order.application.dto.CreateOrderResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final RedisLockService redisLockService;

    public OrderController(OrderService orderService, RedisLockService redisLockService) {
        this.orderService = orderService;
        this.redisLockService = redisLockService;
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
        String key = "order:monolithic:" + request.orderId();
        boolean acquiredLock = redisLockService.tryLock(key, request.orderId().toString());

        if(!acquiredLock) {
            throw new RuntimeException("락 획득 실패 ");
        }

        try {
            orderService.placeOrder(request.toPlaceOrderCommand());
        } finally {
            redisLockService.unlock(key);
        }

    }
}