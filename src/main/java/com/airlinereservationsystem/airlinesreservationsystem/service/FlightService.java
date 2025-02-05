package com.airlinereservationsystem.airlinesreservationsystem.service;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.FlightStatus;
import com.airlinereservationsystem.airlinesreservationsystem.exception.AlreadyExistsException;
import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Aeroplane;
import com.airlinereservationsystem.airlinesreservationsystem.model.Airline;
import com.airlinereservationsystem.airlinesreservationsystem.model.Airport;
import com.airlinereservationsystem.airlinesreservationsystem.model.Flight;
import com.airlinereservationsystem.airlinesreservationsystem.repository.FlightRepository;
import com.airlinereservationsystem.airlinesreservationsystem.request.FlightAddRequest;
import com.airlinereservationsystem.airlinesreservationsystem.response.FlightResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportService airportService;
    private final AeroplaneService aeroplaneService;
    private final AirlineService airlineService;
    private final ModelMapper mapper;

    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::toFlightResponse)
                .toList();
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Flight with id not found: " + id));
    }

    public Flight getFlightByFlightNumber(String flightNumber) {
        return Optional.ofNullable(flightRepository.findByFlightNumber(flightNumber)).orElseThrow(() -> new ResourceNotFoundException("Flight with flight number not found: " + flightNumber));
    }

    public void deleteFlightByFlightNumber(String flightNumber) {
        Optional.of(flightNumber)
                .map(this::getFlightByFlightNumber)
                .ifPresent(flightRepository::delete);
    }

    public FlightResponse addFlight(FlightAddRequest request) {
        try {
            flightValidationByDepartureTimeAndAeroplaneName(request);
            dateValidation(request);
            airportValidation(request);
            Aeroplane aeroplane = aeroplaneValidation(request);
            Airline airline = airlineValidation(request);
            Flight flight = new Flight();
            flight.setFlightNumber(generateFlightNumber());
            flight.setDepartureTime(request.getDepartureTime());
            flight.setArrivalTime(request.getArrivalTime());
            flight.setStatus(request.getStatus());
            flight.setOriginAirport(airportService.getAirportByCode(request.getOriginAirportCode()));
            flight.setDestinationAirport(airportService.getAirportByCode(request.getDestinationAirportCode()));
            flight.setAeroplane(aeroplane);
            flight.setAirline(airline);
            return toFlightResponse(flightRepository.save(flight));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }

    public void deleteAllFlights(String airlineName) {
        List<Flight> flights = flightRepository.findByAirline_AirlineName(airlineName);
        flightRepository.deleteAll(flights);
    }

    public FlightResponse updateFlight(FlightAddRequest request, String flightNumber) {
        Flight flight = Optional.ofNullable(flightRepository.findByFlightNumber(flightNumber))
                .orElseThrow(() -> new ResourceNotFoundException("Flight with flight number not found: " + flightNumber));
        dateValidation(request);
        airportValidation(request);
        Aeroplane aeroplane = aeroplaneValidation(request);
        Airline airline = airlineValidation(request);
        flight.setDepartureTime(request.getDepartureTime());
        flight.setArrivalTime(request.getArrivalTime());
        flight.setStatus(request.getStatus());
        flight.setOriginAirport(airportService.getAirportByCode(request.getOriginAirportCode()));
        flight.setDestinationAirport(airportService.getAirportByCode(request.getDestinationAirportCode()));
        flight.setAeroplane(aeroplane);
        flight.setAirline(airline);
        flightRepository.save(flight);
        return toFlightResponse(flight);
    }


    private String generateFlightNumber() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 6);
    }

    private void dateValidation(FlightAddRequest request) {
        if (!request.getDepartureTime().isBefore(request.getArrivalTime())) {
            throw new ResourceNotFoundException("Departure time cannot be before arrival time");
        }
    }

    private void airportValidation(FlightAddRequest request) {

        Airport originAirport = airportService.getAirportByCode(request.getOriginAirportCode());
        Airport destinationAirport = airportService.getAirportByCode(request.getDestinationAirportCode());
        if (originAirport.equals(destinationAirport)) {
            throw new ResourceNotFoundException("Origin and destination airports cannot be the same");
        }
    }

    private Aeroplane aeroplaneValidation(FlightAddRequest request) {

        return aeroplaneService.getAeroplaneByName(request.getAeroplaneName());
    }

    private Airline airlineValidation(FlightAddRequest request) {
        return airlineService.getAirlineByName(request.getAirlineName());
    }

    public FlightResponse toFlightResponse(Flight flight) {
        return mapper.map(flight, FlightResponse.class);
    }

    public void flightValidationByDepartureTimeAndAeroplaneName(FlightAddRequest request) {
        if (flightRepository.existsByDepartureTimeAndAeroplane_AeroplaneName(request.getDepartureTime(), request.getAeroplaneName())) {
            throw new AlreadyExistsException("Flight with departure time and aeroplane name already exists");
        }

    }

    public void clearAllFlights() {
        flightRepository.deleteAll();
    }


    public FlightResponse statusUpdate(String flightNumber, String status) {
        Flight flight = Optional.ofNullable(flightRepository.findByFlightNumber(flightNumber))
                .orElseThrow(() -> new ResourceNotFoundException("Flight with flight number not found: " + flightNumber));
        flight.setStatus(FlightStatus.valueOf(status));
        flightRepository.save(flight);
        return toFlightResponse(flight);
    }

    public Flight flightStatusUpdate(String flightNumber, String status) {
        Flight flight = Optional.ofNullable(flightRepository.findByFlightNumber(flightNumber))
                .orElseThrow(() -> new ResourceNotFoundException("Flight with flight number not found: " + flightNumber));
        flight.setStatus(FlightStatus.valueOf(status));
        return flightRepository.save(flight);
    }
}



