package com.airlinereservationsystem.airlinesreservationsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Aeroplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID for the airplane

    private String aeroplaneName; // Name of the airplane

    private String aeroplaneType; // Type of airplane (e.g., Cargo, Passenger, etc.)

    private BigDecimal capacity; // Total seating capacity of the airplane

    private BigDecimal luggageCapacity;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline; // Airline to which the airplane belongs

    @JsonBackReference
    @OneToMany(mappedBy = "aeroplane", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> flights;

    @JsonBackReference
    @OneToMany(mappedBy = "aeroplane", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>(); // List of seats in the airplane

}
