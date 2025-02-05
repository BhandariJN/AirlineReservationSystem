package com.airlinereservationsystem.airlinesreservationsystem.controller;


import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Airport;
import com.airlinereservationsystem.airlinesreservationsystem.request.AirportRequest;
import com.airlinereservationsystem.airlinesreservationsystem.response.ApiResponse;
import com.airlinereservationsystem.airlinesreservationsystem.service.AirportService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/airport")
public class AirportController {
    private final AirportService airportService;


    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getAllAirports() {
        List<Airport> airports = airportService.getAllAirports();
        if(airports.isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("No airports found", null));
        }
        return ResponseEntity.ok(new ApiResponse("Airports retrieved successfully", airports));
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAirport(@Valid @RequestBody AirportRequest request) {
        try {
            Airport airport=  airportService.addAirport(request);
            return ResponseEntity.ok(new ApiResponse("Airport added successfully", airport));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/get/{name}")
    public ResponseEntity<ApiResponse> getAirportByName(@PathVariable String name) {
        try {
            Airport airport = airportService.getAirportByName(name);
            return ResponseEntity.ok(new ApiResponse("Airport retrieved successfully", airport));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{code}")
    public ResponseEntity<ApiResponse> deleteAirport(@PathVariable String code) {
        try {
            airportService.deleteAirport(code);
            return ResponseEntity.ok(new ApiResponse("Airport deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get/{code}/code")
    public ResponseEntity<ApiResponse> getAirportByCode(@PathVariable String code) {
        try {
            Airport airport = airportService.getAirportByCode(code);
            return ResponseEntity.ok(new ApiResponse("Airport retrieved successfully", airport));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{code}")
    public ResponseEntity<ApiResponse> updateAirport(@Valid @RequestBody AirportRequest request, @PathVariable String code) {
        try {
            Airport airport = airportService.updateAirport(request, code);
            return ResponseEntity.ok(new ApiResponse("Airport updated successfully", airport));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
