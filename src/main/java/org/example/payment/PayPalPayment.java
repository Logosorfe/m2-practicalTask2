package org.example.payment;

import org.example.config.AppConfig;
import org.example.model.PaymentResult;

import java.util.Scanner;

public class PayPalPayment extends PaymentMethod {
    private final String email;
    Scanner scanner = new Scanner(System.in);

    public PayPalPayment() {
        super("PayPal");
        System.out.println("Email account:");
        this.email = scanner.nextLine();
    }

    @Override
    public PaymentResult processPayment(double amount) {
        if (email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$\n"))
            throw new IllegalArgumentException("Invalid PayPal account");

        return new PaymentResult(true, "Paid " + String.format("%.2f", amount) + " "
                + AppConfig.getInstance().getCurrency() + " using PayPal");
    }
}
