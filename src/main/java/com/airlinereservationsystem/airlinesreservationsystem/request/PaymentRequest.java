package com.airlinereservationsystem.airlinesreservationsystem.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private Long bookingId;
    private String method;
    private BigDecimal amount;
}
