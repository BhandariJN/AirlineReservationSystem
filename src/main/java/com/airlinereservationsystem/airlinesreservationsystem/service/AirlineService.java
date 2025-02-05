package com.airlinereservationsystem.airlinesreservationsystem.service;

import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Airline;
import com.airlinereservationsystem.airlinesreservationsystem.repository.AirlineRepositroy;
import com.airlinereservationsystem.airlinesreservationsystem.request.AirlineRequest;
import com.airlinereservationsystem.airlinesreservationsystem.request.AirlineUpdateRequest;
import com.airlinereservationsystem.airlinesreservationsystem.response.AirlineResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirlineService {
  private final AirlineRepositroy airlineRepositroy;
    private final ModelMapper mapper;

  public List<Airline> getAllAirlines() {
        return airlineRepositroy.findAll();
    }


    public Airline getAirLineById(Long id) {
        return airlineRepositroy.findById(id).orElseThrow(() -> new ResourceNotFoundException("Airline with id not found: " + id));

    }

    public Airline getAirlineByName(String name) {
        return Optional.ofNullable(airlineRepositroy.findByAirlineName(name))
                .orElseThrow(() -> new ResourceNotFoundException("Airline with name not found: " + name));
    }

    public Airline addAirline(AirlineRequest request) {

            return Optional.of(request)
                    .filter(airlineRequest -> existsByAirlineName(airlineRequest.getAirlineName()))
                    .map(airlineRequest -> {
                        Airline airline = new Airline();
                        airline.setAirlineName(airlineRequest.getAirlineName());
                        airline.setEmail(airlineRequest.getEmail());
                        airline.setPhoneNumber(airlineRequest.getPhoneNumber());
                        airline.setAddress(airlineRequest.getAddress());
                        return airlineRepositroy.save(airline);
                    })
                    .orElseThrow(() -> new ResourceNotFoundException("Airline with name already exists: " + request.getAirlineName()));


    }

    public void deleteAirline(String airlineName) {
        Airline airline = Optional.ofNullable(airlineRepositroy.findByAirlineName(airlineName))
                .orElseThrow(() -> new ResourceNotFoundException("Airline with name not found: " + airlineName));
        airlineRepositroy.delete(airline);
    }

    public Airline updateAirline(AirlineUpdateRequest request, String airlineName) {
        Airline airline = Optional.ofNullable(airlineRepositroy.findByAirlineName(request.getAirlineName()))
                .orElseThrow(() -> new ResourceNotFoundException("Airline with name not found: " + request.getAirlineName()));

        airline.setAirlineName(request.getAirlineName());
        airline.setPhoneNumber(request.getPhoneNumber());
        airline.setAddress(request.getAddress());
        return airlineRepositroy.save(airline);
    }

    public boolean existsByAirlineName(String airlineName) {
        return !airlineRepositroy.existsByAirlineName(airlineName);
    }

    public   AirlineResponse toAirlineResponse(Airline airline){
      return mapper.map(airline, AirlineResponse.class);
    }
}






