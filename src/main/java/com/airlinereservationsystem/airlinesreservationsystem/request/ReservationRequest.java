package com.airlinereservationsystem.airlinesreservationsystem.request;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.SeatClass;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class ReservationRequest {


    private Long userId;

    @NotNull(message = "Flight ID is required")
    private String flightId;

    @NotNull(message = "Seat class is required")
    private SeatClass seatClass;

    @NotEmpty(message = "At least one seat number must be provided")
    private Set<String> seatNumbers;
}
