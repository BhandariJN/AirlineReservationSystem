package com.airlinereservationsystem.airlinesreservationsystem.repository;


import com.airlinereservationsystem.airlinesreservationsystem.model.Flight;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository  extends JpaRepository<Flight, Long> {

    Flight findByFlightNumber(String flightNumber);

    boolean existsByDepartureTimeAndAeroplane_AeroplaneName(@NotNull(message = "Departure time is required") @Future(message = "Departure time must be in the future") LocalDateTime departureTime, @NotNull(message = "Aeroplane ID is required") String aeroplaneName);

    List<Flight> findByAirline_AirlineName(String airlineName);
}
