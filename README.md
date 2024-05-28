# Payment Service using Razorpay and Stripe

## Problem Statement

Implement the Payment Service that allows users to make payments using Razorpay and Stripe Payment Gateway.

## Requirements
1. Add the required dependencies for Razorpay and Stripe in the pom.xml file.
2. Implement a function to generate payment links for Razorpay and Stripe.
3. Refer Razorpay API document: [Create a Standard Payment Link](https://razorpay.com/docs/api/payments/payment-links/create-standard/)
4. Refer Stripe API document: [Create a payment link](https://docs.stripe.com/api/payment_links/payment_links/create)
5. Generate KeyId and KeySecret for Razorpay by logging into the Razorpay Dashboard. Similarly, generate SecretKey for Stripe by logging into the Stripe Dashboard. Use these details to authenticate the payment requests to Razorpay and Stripe.
6. From both the implementations of Razorpay and Stripe Payment Gateway APIs, return the payment link to the user.
