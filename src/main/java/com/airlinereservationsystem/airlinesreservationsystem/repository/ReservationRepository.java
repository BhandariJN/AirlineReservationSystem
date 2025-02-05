package com.airlinereservationsystem.airlinesreservationsystem.repository;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.ReservationStatus;
import com.airlinereservationsystem.airlinesreservationsystem.Enum.SeatClass;
import com.airlinereservationsystem.airlinesreservationsystem.model.Flight;
import com.airlinereservationsystem.airlinesreservationsystem.model.Reservation;
import com.airlinereservationsystem.airlinesreservationsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByFlight_Airline_AirlineName(String flightAirlineAirlineName);

    List<Reservation> findByUser_email(String userName);

    List<Reservation> findByUser_Id(Long userId);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.user.id = :userId AND r.flight.flightNumber = :flightId AND r.seatClass = :seatClass")
    boolean existsByUserIdAndFlightIdAndSeatClass(@Param("userId") Long userId,
                                                  @Param("flightId") String flightId,
                                                  @Param("seatClass") SeatClass seatClass);

    List<Reservation> findByStatus(ReservationStatus reservationStatus);
}

