package org.example.payment;

import org.example.config.AppConfig;
import org.example.model.PaymentResult;

import java.util.Scanner;

public class GiftCardPayment extends PaymentMethod {
    private final String code;
    private double balance;
    Scanner scanner = new Scanner(System.in);

    public GiftCardPayment() {
        super("GiftCard");
        System.out.println("Card code:");
        this.code = scanner.nextLine();

        System.out.println("Card balance in " + AppConfig.getInstance().getCurrency() + ":");
        this.balance = scanner.nextDouble();
    }

    @Override
    public PaymentResult processPayment(double amount){
        if (!code.matches("^[A-Za-z0-9]+$"))
            throw new IllegalArgumentException("Invalid GiftCard Code");

        if (balance < amount)
            throw new InsufficientFundsException("Not enough money on card");

        this.balance -= amount;

        return new PaymentResult(true, "Paid " + String.format("%.2f", amount) + " "
                + AppConfig.getInstance().getCurrency() + " using gift card");
    }
}
