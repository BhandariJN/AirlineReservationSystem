package com.airlinereservationsystem.airlinesreservationsystem.controller;

import com.airlinereservationsystem.airlinesreservationsystem.exception.AlreadyExistsException;
import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Payment;
import com.airlinereservationsystem.airlinesreservationsystem.request.PaymentRequest;
import com.airlinereservationsystem.airlinesreservationsystem.response.ApiResponse;
import com.airlinereservationsystem.airlinesreservationsystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/payment")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/pay")
    public ResponseEntity<ApiResponse> processPayment(@RequestBody PaymentRequest paymentRequest){
        try {
            Payment payment = paymentService.processPayment(paymentRequest);
            return ResponseEntity.ok(new ApiResponse("Payment Successful", payment));
        } catch (ResourceNotFoundException | AlreadyExistsException e) {
          return   ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }

    }
}
