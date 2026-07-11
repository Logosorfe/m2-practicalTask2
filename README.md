# Practical Task: Payment Processing Console App

Starter project for M3B practical task: Create a simple Java console application that simulates payment processing for an online shop.

## Task
The application should support the following payment methods:
1. Credit Card
2. PayPal
3. Gift Card

Each payment method should behave differently:
1. Credit card payment requires a card number and card holder name
2. PayPal payment should require an email address
3. Gift card payment should require a gift card code and available balance

## Behavioural Requirements
User should be able to:
- Create an order
- Add items to the order
- View the order summary
- Choose a payment method
- Pay for the order

## Stretch goals
- Add a new payment method, for example crypto payment or bank transfer
- Add tax calculation using AppConfig
- Save completed orders in memory
  
## Teacher's comments
``` text
No-order user path crashed during runtime check. Guard should stop execution before using order-dependent logic. Prioritize this
first because it affects core task reliability. Non-numeric menu/input values crashed the app during runtime check; use safe
parsing and retry prompts. Address this next to make the flow robust for users. Happy-path runtime scenario crashed; review
end-to-end flow from order creation to payment. Address this next to make the flow robust for users. Invalid payment selection
throws an exception; show a user-friendly message and return to menu instead. Address this next to make the flow robust for users.
```
