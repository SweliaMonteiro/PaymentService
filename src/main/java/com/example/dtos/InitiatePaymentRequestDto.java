package com.example.dtos;

import lombok.Getter;
import lombok.Setter;


// Created to get orderId and amount from the user

@Getter
@Setter
public class InitiatePaymentRequestDto {

    private Long orderId;

    private Long amount;

}
