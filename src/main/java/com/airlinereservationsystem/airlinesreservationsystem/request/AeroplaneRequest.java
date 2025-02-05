package com.airlinereservationsystem.airlinesreservationsystem.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
public class AeroplaneRequest {
    @NotNull
    private String aeroplaneName; // Name of the airplane

    @NotNull
    private String aeroplaneType; // Type of airplane (e.g., Cargo, Passenger, etc.)

    @NotNull
    private BigDecimal capacity; // Total seating capacity of the airplane

    @NotNull
    private BigDecimal luggageCapacity; // Luggage capacity of the airplane

    @NotNull
    private String airlineName; // Airline to which the airplane belongs





}
