package com.airlinereservationsystem.airlinesreservationsystem.response;


import com.airlinereservationsystem.airlinesreservationsystem.model.Booking;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<BookingResponse> bookings;
    private  List<ReservationResponse> reservations;
}
