package com.airlinereservationsystem.airlinesreservationsystem.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
