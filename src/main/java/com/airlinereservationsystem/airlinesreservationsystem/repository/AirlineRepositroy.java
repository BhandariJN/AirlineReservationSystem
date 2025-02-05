package com.airlinereservationsystem.airlinesreservationsystem.repository;

import com.airlinereservationsystem.airlinesreservationsystem.model.Airline;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepositroy extends JpaRepository<Airline, Long> {

    Airline findByAirlineName(String name);

    boolean existsByAirlineName(@NotNull String airlineName);

}
