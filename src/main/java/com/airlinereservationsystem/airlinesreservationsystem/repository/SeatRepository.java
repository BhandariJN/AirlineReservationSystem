package com.airlinereservationsystem.airlinesreservationsystem.repository;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.SeatClass;
import com.airlinereservationsystem.airlinesreservationsystem.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    Set<Seat> findByAeroplane_AeroplaneName(String aeroplaneName);


    Seat findBySeatNumber(String seatName);

    List<Seat> findByAeroplane_AeroplaneNameAndSeatClass(String aeroplaneAeroplaneName, SeatClass seatClass);

    List<Seat> findBySeatNumberIn(Set<String> seatNumber);
}
