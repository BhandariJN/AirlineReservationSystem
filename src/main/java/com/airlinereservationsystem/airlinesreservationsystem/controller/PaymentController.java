package com.airlinereservationsystem.airlinesreservationsystem.controller;

import com.airlinereservationsystem.airlinesreservationsystem.model.Payment;
import com.airlinereservationsystem.airlinesreservationsystem.request.PaymentRequest;
import com.airlinereservationsystem.airlinesreservationsystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/pay")
    public ResponseEntity<Payment> processPayment(@RequestBody PaymentRequest paymentRequest){
        Payment payment = paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(null);
    }
}
