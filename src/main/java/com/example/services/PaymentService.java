package com.example.services;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.example.paymentGateway.PaymentGateway;


@Service
public class PaymentService {

    // @Qualifier annotation is used to specify the name of the bean to be injected
    @Autowired
    @Qualifier("stripePaymentGateway")
    private PaymentGateway paymentGateway;


    // Call the generatePaymentLink method of the PaymentGateway interface to generate a payment link
    public String initiatePayment(Long orderId, Long amount) throws RazorpayException, StripeException {
        return paymentGateway.generatePaymentLink(orderId, amount);
    }
}
