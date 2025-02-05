package com.airlinereservationsystem.airlinesreservationsystem.service;


import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Airport;
import com.airlinereservationsystem.airlinesreservationsystem.repository.AirportRepository;
import com.airlinereservationsystem.airlinesreservationsystem.request.AirportRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;
    private final ModelMapper mapper;

    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    public boolean existsByCode(String code) {
        return airportRepository.existsByCode(code);
    }


    public Airport addAirport(AirportRequest request) {

        return Optional.of(request)
                .filter(airportRequest -> !existsByCode(airportRequest.getCode()))
                .map(airportRequest -> {
                    Airport airport = toAirport(airportRequest);
                    return airportRepository.save(airport);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Airport with code already exists: " + request.getCode()));
    }

    public Airport getAirportByName(String name) {
        return Optional.ofNullable(airportRepository.findByName(name))
                .orElseThrow(() -> new ResourceNotFoundException("Airport with code not found: " + name));
    }

    public void deleteAirport(String code) {
        Airport airport = Optional.ofNullable(getAirportByCode(code))
                .orElseThrow(() -> new ResourceNotFoundException("Airport with code not found: " + code));
        airportRepository.delete(airport);
    }


    public Airport getAirportByCode(String code) {
        return Optional.ofNullable(airportRepository.findByCode(code))
                .orElseThrow(() -> new ResourceNotFoundException("Airport with code not found: " + code));
    }


    public Airport updateAirport(AirportRequest request, String code) {
        Airport airport = Optional.ofNullable(getAirportByCode(code))
                .orElseThrow(() -> new ResourceNotFoundException("Airport with code not found: " + code));

        Airport updatedAirport = toAirport(request);
        return airportRepository.save(updatedAirport);
    }


    public Airport toAirport(AirportRequest request) {
        return mapper.map(request, Airport.class);
    }
}
