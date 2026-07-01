package org.example.payment;

import org.example.model.Order;
import org.example.model.PaymentResult;

public class PaymentProcessor {
    public PaymentResult process(Order order, PaymentMethod paymentMethod){
        if (order.isPaid())
            throw new IllegalStateException("This order is already paid");
        if (order.getItems().isEmpty())
            throw new IllegalStateException("There is nothing to pay for");

        PaymentResult result = paymentMethod.pay(order.calculateTotal());

        if(result.isSuccessful()){
            order.markAsPaid();
        }

        return result;
    }
}
