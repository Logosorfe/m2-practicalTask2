package org.example.model;

import org.example.config.AppConfig;

public class FixedAmountDiscount extends Discount {
    public FixedAmountDiscount() {
        super("TAXFREE");
    }

    @Override
    public double apply(double originalAmount) {
        return originalAmount / AppConfig.getInstance().getTaxRate();
    }
}
