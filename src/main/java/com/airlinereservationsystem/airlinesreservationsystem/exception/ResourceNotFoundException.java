package com.airlinereservationsystem.airlinesreservationsystem.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
