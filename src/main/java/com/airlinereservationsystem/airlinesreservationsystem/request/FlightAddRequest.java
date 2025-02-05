package com.airlinereservationsystem.airlinesreservationsystem.request;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.FlightStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightAddRequest {
    @NotNull(message = "Departure time is required")
    @Future(message = "Departure time must be in the future")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @Future(message = "Arrival time must be in the future")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Flight status is required")
    private FlightStatus status;

    @NotBlank(message = "Origin airport code is required")
    private String originAirportCode;

    @NotBlank(message = "Destination airport code is required")
    private String destinationAirportCode;

    @NotNull(message = "Airline ID is required")
    private String airlineName;

    @NotNull(message = "Aeroplane ID is required")
    private String aeroplaneName;
}
