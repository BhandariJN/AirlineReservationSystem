package com.airlinereservationsystem.airlinesreservationsystem.controller;

import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Reservation;
import com.airlinereservationsystem.airlinesreservationsystem.repository.ReservationRepository;
import com.airlinereservationsystem.airlinesreservationsystem.repository.SeatRepository;
import com.airlinereservationsystem.airlinesreservationsystem.request.ReservationRequest;
import com.airlinereservationsystem.airlinesreservationsystem.response.ApiResponse;
import com.airlinereservationsystem.airlinesreservationsystem.response.ReservationResponse;
import com.airlinereservationsystem.airlinesreservationsystem.response.ReservationResponseUser;
import com.airlinereservationsystem.airlinesreservationsystem.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/reservation")
public class ReservationController {

    private final ReservationService reservationService;



    @PostMapping("/reserve")
    public ResponseEntity<ApiResponse> getReservation(@Valid @RequestBody ReservationRequest request) {
        try {
            Reservation reservation = reservationService.makeReservation(request);
            ReservationResponseUser response = reservationService.toResponseUser(reservation);
            return ResponseEntity.ok(new ApiResponse("Reservation made successfully", response));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/get/{airlineName}")
    public ResponseEntity<ApiResponse> getAllReservationByAirlineName(@PathVariable String airlineName) {
        try {
            List<Reservation> reservations = reservationService.getAllReservationByAirlineName(airlineName);
            List<ReservationResponse> response = reservations.stream().map(reservationService::toResponse).toList();
            return ResponseEntity.ok(new ApiResponse("Reservations found!", response));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get/{userId}/user")
    public ResponseEntity<ApiResponse> getAllReservationByUserName(@PathVariable Long userId) {
        try {
            List<Reservation> reservations = reservationService.getReservationByUserId(userId);
            List<ReservationResponseUser> response = reservations.stream().map(reservationService::toResponseUser).toList();
            return ResponseEntity.ok(new ApiResponse("Reservations found!", response));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}



