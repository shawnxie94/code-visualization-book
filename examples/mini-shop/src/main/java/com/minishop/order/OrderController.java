package com.minishop.order;

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public Order create(String orderId, int quantity, double unitPrice, String customerType) {
        return orderService.createOrder(orderId, quantity, unitPrice, customerType);
    }
}
