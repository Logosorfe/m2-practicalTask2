package org.example.payment;

import org.example.config.AppConfig;
import org.example.model.PaymentResult;

public class CreditCardPayment extends PaymentMethod {
    private final String cardNumber;
    private final String cardHolderName;

    public CreditCardPayment(String cardNumber, String cardHolderName) {
        super("CreditCard");
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public PaymentResult processPayment(double amount) {
        if (!cardNumber.matches("^\\d{4} \\d{4} \\d{4} \\d{4}$"))
            throw new IllegalArgumentException("Invalid card number");

        if (!cardHolderName.matches("^[A-Z]+ [A-Z]+$"))
            throw new IllegalArgumentException("Invalid cardholder name");

        return new PaymentResult(true, "Paid " + String.format("%.2f", amount) + " "
                + AppConfig.getInstance().getCurrency() + " using credit card ending with "
                + cardNumber.substring(cardNumber.length() - 4));
    }
}
