package com.airlinereservationsystem.airlinesreservationsystem.response;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.ReservationStatus;
import com.airlinereservationsystem.airlinesreservationsystem.Enum.SeatClass;
import com.airlinereservationsystem.airlinesreservationsystem.model.Seat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservationResponseUser {

    private Long reservationId;
    private SeatClass seatClass;
    private BigDecimal totalPrice;
    private ReservationStatus status;
    private LocalDateTime bookingTime;
    private String userName;
    private String flightStatus;
    private String airlineName;
    private String departureAirport;
    private LocalDateTime departureTime;
    private String arrivalAirport;
    private LocalDateTime arrivalTime;
    private List<String> seats;

}
