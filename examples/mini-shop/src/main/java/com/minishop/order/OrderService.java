package com.minishop.order;

import com.minishop.payment.PaymentClient;
import com.minishop.pricing.PricingService;

public class OrderService {
    private final PricingService pricingService;
    private final PaymentClient paymentClient;
    private final OrderRepository orderRepository;

    public OrderService(
            PricingService pricingService,
            PaymentClient paymentClient,
            OrderRepository orderRepository) {
        this.pricingService = pricingService;
        this.paymentClient = paymentClient;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(String orderId, int quantity, double unitPrice, String customerType) {
        double total = pricingService.calculateTotal(quantity, unitPrice, customerType);
        paymentClient.charge(orderId, total);
        Order order = new Order(orderId, total);
        orderRepository.save(order);
        return order;
    }
}
