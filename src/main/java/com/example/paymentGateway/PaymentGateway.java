package com.example.paymentGateway;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;


// Use an interface to define the methods that must be implemented by the payment gateways so that the PaymentService class is not tightly coupled with the payment gateway classes
public interface PaymentGateway {

    String generatePaymentLink(Long orderId, Long amount) throws RazorpayException, StripeException;

}
