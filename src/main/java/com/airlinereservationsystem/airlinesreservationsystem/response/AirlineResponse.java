package com.airlinereservationsystem.airlinesreservationsystem.response;

import com.airlinereservationsystem.airlinesreservationsystem.model.Aeroplane;
import com.airlinereservationsystem.airlinesreservationsystem.model.Flight;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class AirlineResponse {

    private String airlineName;
    private String email;
    private String phoneNumber;
    private String address;
    private List<Aeroplane> aeroplanes;
    private List<FlightResponse> flights;
}
