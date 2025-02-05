package com.airlinereservationsystem.airlinesreservationsystem.controller;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.SeatClass;
import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Seat;
import com.airlinereservationsystem.airlinesreservationsystem.request.SeatRequest;
import com.airlinereservationsystem.airlinesreservationsystem.response.ApiResponse;
import com.airlinereservationsystem.airlinesreservationsystem.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/seat/")
public class SeatController {
    private final SeatService seatService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addSeat(@Valid @RequestBody SeatRequest request){
        try {
            List<Seat> seats = seatService.AddSeat(request);
            return ResponseEntity.ok(new ApiResponse("Seats added successfully", seats));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get/{aeroplaneName}")
    public ResponseEntity<ApiResponse> getSeats(@PathVariable String aeroplaneName){
        try {
            Set<Seat> seats = seatService.getSeatsOfAeroplane(aeroplaneName);
            return ResponseEntity.ok(new ApiResponse("Seats fetched successfully", seats));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }


    @GetMapping("/get/{aeroplaneName}/{seatClass}")
    public ResponseEntity<ApiResponse> getSeatByClass(@PathVariable String aeroplaneName, @PathVariable SeatClass seatClass){
        try {
            List<Seat> seats = seatService.getSeatbySeatClass(aeroplaneName, seatClass);
            return ResponseEntity.ok(new ApiResponse("Seats fetched successfully", seats));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }


    @DeleteMapping("/delete/{aeroplaneName}/all")
    public ResponseEntity<ApiResponse> deleteAllSeatsOfAeroplane(@PathVariable String aeroplaneName){
        try {
            seatService.deleteAllSeatsOfAeroplane(aeroplaneName);
            return ResponseEntity.ok(new ApiResponse("Seats deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
