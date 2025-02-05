package com.airlinereservationsystem.airlinesreservationsystem.repository;

import com.airlinereservationsystem.airlinesreservationsystem.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    Airport findByCode(String code);

    boolean existsByCode(String code);

    Airport findByName(String name);

}
