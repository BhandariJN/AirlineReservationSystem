package com.airlinereservationsystem.airlinesreservationsystem.controller;


import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Airline;
import com.airlinereservationsystem.airlinesreservationsystem.request.AirlineRequest;
import com.airlinereservationsystem.airlinesreservationsystem.request.AirlineUpdateRequest;
import com.airlinereservationsystem.airlinesreservationsystem.response.AirlineResponse;
import com.airlinereservationsystem.airlinesreservationsystem.response.ApiResponse;
import com.airlinereservationsystem.airlinesreservationsystem.service.AirlineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/airline")
public class AirlineController {
    private final AirlineService airlineService;



    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getAllAirlines() {
        if(airlineService.getAllAirlines().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No airlines found", null));
        }
        List<Airline> airline = airlineService.getAllAirlines();
        List<AirlineResponse> airlineResponses = airline.stream()
                .map(airlineService::toAirlineResponse)
                .toList();

        return ResponseEntity.ok(new ApiResponse("Airlines retrieved successfully", airlineResponses));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAirline(@Valid @RequestBody AirlineRequest request) {
        try {
            Airline savedAirline = airlineService.addAirline(request);
            return ResponseEntity.ok(new ApiResponse("Airline added successfully", savedAirline));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/delete/{airlineName}")
    public ResponseEntity<ApiResponse> deleteAirline(@PathVariable String airlineName) {
        try {

            airlineService.deleteAirline(airlineName);
            return ResponseEntity.ok(new ApiResponse("Airline deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get/{airlineName}")
    public ResponseEntity<ApiResponse> getAirlineByName(@PathVariable String airlineName) {
        try {
            Airline airline = airlineService.getAirlineByName(airlineName);
            return ResponseEntity.ok(new ApiResponse("Airline retrieved successfully", airline));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{airlineName}")
    public ResponseEntity<ApiResponse> updateAirline(@RequestBody AirlineUpdateRequest request, @PathVariable String airlineName) {
        try {
            Airline updatedAirline = airlineService.updateAirline(request, airlineName);
            return ResponseEntity.ok(new ApiResponse("Airline updated successfully", updatedAirline));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }
}
