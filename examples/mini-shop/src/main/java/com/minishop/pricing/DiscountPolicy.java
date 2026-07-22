package com.minishop.pricing;

public class DiscountPolicy {
    public double apply(String customerType, double amount) {
        if ("VIP".equals(customerType)) {
            return amount * 0.9;
        }
        return amount;
    }
}
