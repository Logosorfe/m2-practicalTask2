package org.example.payment;

public class PaymentMethodFactory {
    public static PaymentMethod createCreditCardPayment(String cardNumber, String cardHolderName) {
        return new CreditCardPayment(cardNumber, cardHolderName);
    }

    public static PaymentMethod createOtherPayment(String paymentMethod) {
        return switch (paymentMethod){
            case "PayPal" -> new PayPalPayment();
            case "Gift Card" -> new GiftCardPayment();
            default -> throw  new IllegalArgumentException("Invalid payment method");
        };
    }
}
