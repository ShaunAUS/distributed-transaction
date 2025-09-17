package com.example.monolithic.order.application;

import com.example.monolithic.order.application.dto.CreateOrderCommand;
import com.example.monolithic.order.application.dto.CreateOrderResult;
import com.example.monolithic.order.application.dto.PlaceOrderCommand;
import com.example.monolithic.order.domain.Order;
import com.example.monolithic.order.domain.OrderItem;
import com.example.monolithic.order.infrastructure.OrderItemRepository;
import com.example.monolithic.order.infrastructure.OrderRepository;
import com.example.monolithic.point.application.PointService;
import com.example.monolithic.product.application.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PointService pointService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        PointService pointService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.pointService = pointService;
        this.productService = productService;
    }

    @Transactional
    public CreateOrderResult createOrder(CreateOrderCommand createOrderCommand) {

        //주문생성
        Order savedOrder = orderRepository.save(new Order());

        List<OrderItem> orderItems = createOrderCommand.orderItems()
                .stream()
                .map(item -> new OrderItem(savedOrder.getId(), item.productId(), item.quantity()))
                .toList();

        orderItemRepository.saveAll(orderItems);

        return new CreateOrderResult(savedOrder.getId());

    }


    @Transactional
    public void placeOrder(PlaceOrderCommand placeOrderCommand) throws InterruptedException {

        Order order = orderRepository.findById(placeOrderCommand.orderId())
                .orElseThrow(() -> new RuntimeException("주문 정보가 존재하지 않습니다"));

        if (order.getStatus() == Order.OrderStatus.COMPLETED) {
            return;
        }

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());

        Long totalPrice = 0L;

        for (OrderItem orderItem : orderItems) {
            Long price = productService.buy(orderItem.getProductId(), orderItem.getQuantity());
            totalPrice += price;
        }

        //포인트차감
        pointService.use(1L, totalPrice);

        order.complete();

        orderRepository.save(order);

        Thread.sleep(3000);
    }
}
