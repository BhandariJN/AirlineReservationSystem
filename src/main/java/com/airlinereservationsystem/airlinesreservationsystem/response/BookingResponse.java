package com.airlinereservationsystem.airlinesreservationsystem.response;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.BookingStatus;
import com.airlinereservationsystem.airlinesreservationsystem.Enum.ReservationStatus;
import com.airlinereservationsystem.airlinesreservationsystem.Enum.SeatClass;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingResponse {
    private Long id;
    private BigDecimal totalPrice;
    private BookingStatus status;
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
