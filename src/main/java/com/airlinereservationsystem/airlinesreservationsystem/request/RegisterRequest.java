package com.airlinereservationsystem.airlinesreservationsystem.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull(message = "First name must not be null")
    private String firstName;
    @NotNull(message = "Last name must not be null")
    private String lastName;
    @NotNull(message = "Email must not be null")
    @Email(message = "Email must be valid")
    private String email;
    @NotNull(message = "Password must not be null")
    private String password;

    @NotNull(message = "Role must not be null")
    private String role;
}
