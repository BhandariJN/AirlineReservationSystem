package com.airlinereservationsystem.airlinesreservationsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String ticketNumber; // Unique ticket identifier
    private LocalDateTime issueDate; // Ticket issue date
    private String seatNumber; // Assigned seat number


    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}

