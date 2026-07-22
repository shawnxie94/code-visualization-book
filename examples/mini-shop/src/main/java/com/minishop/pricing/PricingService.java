package com.minishop.pricing;

public class PricingService {
    private final DiscountPolicy discountPolicy;

    public PricingService(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public double calculateTotal(int quantity, double unitPrice, String customerType) {
        double amount = quantity * unitPrice;
        double total = discountPolicy.apply(customerType, amount);
        System.out.println("calculate total for " + customerType);
        return total;
    }
}
