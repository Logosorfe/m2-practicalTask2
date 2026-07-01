package org.example.menu;

import org.example.config.AppConfig;
import org.example.model.*;
import org.example.payment.InsufficientFundsException;
import org.example.payment.PaymentMethod;
import org.example.payment.PaymentMethodFactory;
import org.example.payment.PaymentProcessor;

import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);

    private final PaymentProcessor paymentProcessor = new PaymentProcessor();

    private Order currentOrder;

    AppConfig config = AppConfig.getInstance();

    public void start() {

        System.out.println("Welcome to " + config.getApplicationName());

        boolean running = true;
        while (running) {
            printMenu();

            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> createOrder();
                case 2 -> addItem();
                case 3 -> viewOrder();
                case 4 -> applyDiscount();
                case 5 -> payOrder();
                case 0 -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void createOrder() {
        System.out.println("Customer name:");
        String customerName = scanner.nextLine();

        currentOrder = Order.builder()
                .customerName(customerName)
                .build();
        System.out.println("Order created for " + customerName);
    }

    private void addItem() {
        if (currentOrder == null)
            throw new IllegalArgumentException("Order does not exist");

        System.out.println("Item name:");
        String itemName = scanner.nextLine();

        System.out.println("Price:");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.println("Quantity:");
        int quantity = Integer.parseInt(scanner.nextLine());

        currentOrder.addItem(new OrderItem(itemName, price, quantity));
        System.out.println("Item added to order");
    }

    private void viewOrder() {
        if (currentOrder == null)
            throw new IllegalArgumentException("Order does not exist");

        System.out.println("Customer: " + currentOrder.getCustomerName());
        System.out.println("Status: " + currentOrder.getStatus());
        System.out.println("Items:");

        for (OrderItem item : currentOrder.getItems()) {
            System.out.println("- " + item);
        }

        String total = String.format("%.2f", currentOrder.calculateTotal()) + " "
                + config.getCurrency();

        System.out.println("Total: " + total);
    }

    private void applyDiscount() {
        if (!currentOrder.getDiscount().getCode().equals("NONE"))
            throw new IllegalArgumentException("Discount code has already been applied");

        System.out.println("What kind of discount would you like to apply?\n1. PROMO\n2. TAXFREE");
        int option = Integer.parseInt(scanner.nextLine());
        switch (option) {
            case 1 -> {
                System.out.println("Discount code:");
                currentOrder.applyDiscount(new PercentageDiscount(scanner.nextLine()));
            }
            case 2 -> currentOrder.applyDiscount(new FixedAmountDiscount());
            default -> System.out.println("Invalid option");
        }

        System.out.println("Discount applied");
    }

    private void payOrder() {
        if (currentOrder == null)
            throw new IllegalArgumentException("Order does not exist");

        System.out.println("""
                Select payment method:
                1. Credit Card
                2. PayPal
                3. Gift Card
                """);
        int option = Integer.parseInt(scanner.nextLine());

        PaymentMethod paymentMethod = switch (option) {
            case 1 -> createCreditCardPayment();
            case 2 -> createPaypalPayment();
            case 3 -> createGiftCardPayment();
            default -> throw new IllegalArgumentException("Invalid payment method");
        };

        try {
            PaymentResult result = paymentProcessor.process(currentOrder, paymentMethod);
            System.out.println(result.getMessage());
        } catch (InsufficientFundsException e) {
            currentOrder.markAsCancelled();
            System.out.println("Order cancelled: " + e.getMessage());
        }
    }

    private PaymentMethod createCreditCardPayment() {
        System.out.println("Card number:");
        String cardNumber = scanner.nextLine();

        System.out.println("Card holder name:");
        String cardHolderName = scanner.nextLine();

        return PaymentMethodFactory.createCreditCardPayment(cardNumber, cardHolderName);
    }

    private PaymentMethod createPaypalPayment() {
        System.out.println("Enter payment service:");
        String paymentMethod = scanner.nextLine();

        return PaymentMethodFactory.createOtherPayment(paymentMethod);
    }

    private PaymentMethod createGiftCardPayment() {
        System.out.println("Enter card type:");
        String paymentMethod = scanner.nextLine();

        return PaymentMethodFactory.createOtherPayment(paymentMethod);
    }

    private void printMenu() {
        System.out.println("""
                1. Create order
                2. Add item to order
                3. View order
                4. Apply discount
                5. Pay order
                0. Exit
                """);
    }
}
