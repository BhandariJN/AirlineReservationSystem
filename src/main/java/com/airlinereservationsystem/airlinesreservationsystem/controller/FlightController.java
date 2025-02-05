package com.airlinereservationsystem.airlinesreservationsystem.controller;

import com.airlinereservationsystem.airlinesreservationsystem.exception.AlreadyExistsException;
import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Flight;
import com.airlinereservationsystem.airlinesreservationsystem.request.FlightAddRequest;
import com.airlinereservationsystem.airlinesreservationsystem.response.ApiResponse;
import com.airlinereservationsystem.airlinesreservationsystem.response.FlightResponse;
import com.airlinereservationsystem.airlinesreservationsystem.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/flight")
public class FlightController {
    private  final FlightService flightService;


    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getAllFlights() {
        List<FlightResponse>  flights = flightService.getAllFlights();
        if(flights.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No flights Found!", null));
        }
        return ResponseEntity.ok(new ApiResponse("Flights Found!", flights));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getFlightById(@PathVariable Long id) {

        try {
            Flight flight = flightService.getFlightById(id);
            return ResponseEntity.ok(new ApiResponse("Flight Found!", flight));
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get/flight/{flightNumber}")
    public ResponseEntity<ApiResponse> getFlightByFlightNumber(@PathVariable String flightNumber) {
        try {
            Flight flight = flightService.getFlightByFlightNumber(flightNumber);
            return ResponseEntity.ok(new ApiResponse("Flight Found!", flight));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addFlight(@Valid @RequestBody FlightAddRequest request) {
        try {
            FlightResponse savedFlight = flightService.addFlight(request);
            return ResponseEntity.ok(new ApiResponse("Flight Added!", savedFlight));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{flightNumber}")
    public ResponseEntity<ApiResponse> deleteFlightByFlightNumber(@PathVariable String flightNumber) {
        try {
            flightService.deleteFlightByFlightNumber(flightNumber);
            return ResponseEntity.ok(new ApiResponse("Flight Deleted!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{AirlineName}")
    public ResponseEntity<ApiResponse> deleteAllFlights(@PathVariable String AirlineName) {
        try {
            flightService.deleteAllFlights(AirlineName);
            return ResponseEntity.ok(new ApiResponse("All Flights Deleted!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/delete/all")
    public ResponseEntity<ApiResponse> deleteAllFlights() {
        try {
            flightService.clearAllFlights();
            return ResponseEntity.ok(new ApiResponse("All Flights Deleted!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PutMapping("/update/{flightNumber}")
    public ResponseEntity<ApiResponse> updateFlight(@Valid @RequestBody FlightAddRequest request, @PathVariable String flightNumber) {
        try {
            FlightResponse updatedFlight = flightService.updateFlight(request, flightNumber);
            return ResponseEntity.ok(new ApiResponse("Flight Updated!", updatedFlight));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/status/{flightNumber}/{status}/update")
    public ResponseEntity<ApiResponse> flightStatusUpdate(@PathVariable String flightNumber,@PathVariable String status) {

        try
        {
            Flight updatedFlight = flightService.flightStatusUpdate(flightNumber,status);
            FlightResponse response = flightService.toFlightResponse(updatedFlight);
            return ResponseEntity.ok(new ApiResponse("Flight Status Updated!", response));
        }
        catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }

}
