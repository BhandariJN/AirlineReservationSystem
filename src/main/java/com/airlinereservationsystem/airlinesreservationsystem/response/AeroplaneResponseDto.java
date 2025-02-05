package com.airlinereservationsystem.airlinesreservationsystem.response;

import lombok.Data;

@Data
public class AeroplaneResponseDto {

    private String aeroplaneName; // Name of the airplane
    private String aeroplaneType; // Type of airplane (e.g., Cargo, Passenger, etc.)
    private Long capacity; // Total seating capacity of the airplane
    private Long luggageCapacity; // Luggage capacity of the airplane
    private String airlineName; // Airline to which the airplane belongs
}


