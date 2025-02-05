package com.airlinereservationsystem.airlinesreservationsystem.model;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.FlightStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    @OneToMany(mappedBy = "flight")
    private List<Booking> bookings;

    // Origin Airport (Many-to-One)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "origin_airport_id", nullable = false)
    private Airport originAirport;

    // Destination Airport (Many-to-One)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "destination_airport_id", nullable = false)
    private Airport destinationAirport;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;  // Correcting the field name to 'airline' as per naming conventions.

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "aeroplane_id", nullable = false)
    private Aeroplane aeroplane;



}
