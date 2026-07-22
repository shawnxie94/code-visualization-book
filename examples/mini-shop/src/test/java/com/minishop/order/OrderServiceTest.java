package com.minishop.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.minishop.payment.PaymentClient;
import com.minishop.pricing.DiscountPolicy;
import com.minishop.pricing.PricingService;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    @Test
    public void shouldCreateVipOrderWithDiscount() {
        OrderService service = new OrderService(
                new PricingService(new DiscountPolicy()),
                new PaymentClient(),
                new OrderRepository());
        Order order = service.createOrder("o-1", 2, 100.0, "VIP");
        assertEquals(180.0, order.getTotal(), 0.001);
    }
}
