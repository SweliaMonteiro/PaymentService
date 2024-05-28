package com.example.controllers;

import com.example.dtos.InitiatePaymentRequestDto;
import com.example.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    // Since we are not connecting with Order Service, we will create InitiatePaymentRequestDto to get orderId and amount from the user
    @PostMapping("/")
    public String initiatePayment(@RequestBody InitiatePaymentRequestDto requestDto) {
        try {
            return paymentService.initiatePayment(requestDto.getOrderId(), requestDto.getAmount());
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
}
