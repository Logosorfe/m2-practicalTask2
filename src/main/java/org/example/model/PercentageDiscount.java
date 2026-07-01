package org.example.model;

public class PercentageDiscount extends Discount {
    private final double percentage;

    public PercentageDiscount(String code) {
        super(code);
        this.percentage = Double.parseDouble(code.substring(code.length() - 2));
    }

    @Override
    public double apply(double originalAmount) {
        return originalAmount - (originalAmount * percentage / 100);
    }
}
