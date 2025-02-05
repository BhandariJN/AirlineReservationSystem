package com.airlinereservationsystem.airlinesreservationsystem.model;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.SeatClass;
import com.airlinereservationsystem.airlinesreservationsystem.Enum.SeatStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber; // Seat identifier (e.g., 1A, 2B)
    private BigDecimal price; // Price of the seat

    @Enumerated(EnumType.STRING)
    private SeatClass seatClass; // Seat class (e.g., ECONOMY, BUSINESS, FIRST_CLASS)

    @Enumerated
    private SeatStatus seatStatus; // Status of the seat (e.g., AVAILABLE, BOOKED, RESERVED)

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "airplane_id", nullable = false)
    private Aeroplane aeroplane; // Associated airplane



    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;  // Each seat belongs to one booking

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;  // Each seat belongs to one booking


}
