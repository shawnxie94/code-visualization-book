package com.minishop.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PricingServiceTest {
    @Test
    public void shouldApplyVipDiscount() {
        PricingService service = new PricingService(new DiscountPolicy());
        double total = service.calculateTotal(2, 100.0, "VIP");
        assertEquals(180.0, total, 0.001);
    }
}
