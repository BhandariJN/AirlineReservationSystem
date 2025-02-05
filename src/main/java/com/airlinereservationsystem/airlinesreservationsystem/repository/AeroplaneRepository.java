package com.airlinereservationsystem.airlinesreservationsystem.repository;

import com.airlinereservationsystem.airlinesreservationsystem.model.Aeroplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AeroplaneRepository extends JpaRepository<Aeroplane, Long> {


    List<Aeroplane> findByAirline_AirlineName(String airlineName);

    Aeroplane findByAeroplaneName(String aeroplaneName);
}
