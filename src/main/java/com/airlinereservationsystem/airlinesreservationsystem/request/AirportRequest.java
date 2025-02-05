package com.airlinereservationsystem.airlinesreservationsystem.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AirportRequest {
    @NotBlank(message = "Airport code is required")
    @Size(min = 3, max = 3, message = "Airport code must be exactly 3 characters")
    private String code; // IATA code (e.g., JFK, LAX)

    @NotBlank(message = "Airport name is required")
    private String name;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Country is required")
    private String country;

}
