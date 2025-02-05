package com.airlinereservationsystem.airlinesreservationsystem.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AirlineRequest {

    @NotNull
    private String airlineName;

    @Email
    @NotNull
    private String email;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String address;

}
