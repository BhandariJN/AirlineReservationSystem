package com.airlinereservationsystem.airlinesreservationsystem.response;


import com.airlinereservationsystem.airlinesreservationsystem.model.Seat;
import lombok.Data;

import java.util.Set;

@Data
public class SeatResponse {
    String aeroplaneName;
    String seatNumber;
    String seatType;
    String bookingStatus;
    String reservationStatus;
}
