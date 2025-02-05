package com.airlinereservationsystem.airlinesreservationsystem.model;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private BigDecimal amount; // Amount to be paid
    private String paymentMethod; // Credit Card, Debit Card, etc.
    private LocalDateTime paymentDate; // Payment date

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // Payment Status (Pending, Completed, Failed)

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking; // A payment is related to one booking


}
