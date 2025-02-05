package com.airlinereservationsystem.airlinesreservationsystem.response;

import com.airlinereservationsystem.airlinesreservationsystem.model.Seat;
import lombok.Data;

import java.util.List;

@Data
public class FlightResponse {
    private String flightNumber;
    private String departureTime;
    private String arrivalTime;
    private String status;
    private String originAirportCode;
    private String destinationAirportCode;
    private String airlineName;
    private String aeroplaneName;
    private List<Seat> seats;
}
