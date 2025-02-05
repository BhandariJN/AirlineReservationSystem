package com.airlinereservationsystem.airlinesreservationsystem.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AirlineUpdateRequest {


    @NotNull
        private String airlineName;

    @NotNull
        private String phoneNumber;

    @NotNull
        private String address;
}
