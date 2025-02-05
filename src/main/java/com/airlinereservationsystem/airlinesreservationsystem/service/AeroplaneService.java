package com.airlinereservationsystem.airlinesreservationsystem.service;

import com.airlinereservationsystem.airlinesreservationsystem.response.AeroplaneResponseDto;
import com.airlinereservationsystem.airlinesreservationsystem.exception.AlreadyExistsException;
import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Aeroplane;
import com.airlinereservationsystem.airlinesreservationsystem.model.Airline;
import com.airlinereservationsystem.airlinesreservationsystem.repository.AeroplaneRepository;
import com.airlinereservationsystem.airlinesreservationsystem.request.AeroplaneRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AeroplaneService {
    private final AeroplaneRepository aeroplaneRepository;
    private final AirlineService airlineService;
    private final ModelMapper modelMapper;


    public List<Aeroplane> getAllAeroplaneByAirlineName(String airlineName) {
        return Optional.ofNullable(aeroplaneRepository.findByAirline_AirlineName(airlineName))
                .orElseThrow(() -> new ResourceNotFoundException("Aeroplane with airline name not found: " + airlineName));

    }
        public void deleteAeroplane(String aeroplaneName){
            Aeroplane aeroplane = Optional.ofNullable(aeroplaneRepository.findByAeroplaneName(aeroplaneName))
                    .orElseThrow(() -> new ResourceNotFoundException("Aeroplane with name not found: " + aeroplaneName));
            aeroplaneRepository.delete(aeroplane);
        }

        public Aeroplane getAeroplaneByName(String aeroplaneName) {
        return Optional.ofNullable(aeroplaneRepository.findByAeroplaneName(aeroplaneName))
                .orElseThrow(() -> new AlreadyExistsException("Aeroplane with name not found: " + aeroplaneName));
        }


        public Aeroplane addAeroplane(AeroplaneRequest request) {
            return Optional.of(request)
                    .filter(aeroplaneRequest -> !existsByAeroplaneName(aeroplaneRequest.getAeroplaneName()))
                    .map(aeroplaneRequest -> {
                        Aeroplane aeroplane = new Aeroplane();
                        aeroplane.setAeroplaneName(aeroplaneRequest.getAeroplaneName());
                        aeroplane.setAeroplaneType(aeroplaneRequest.getAeroplaneType());
                        Airline airline = airlineService.getAirlineByName(aeroplaneRequest.getAirlineName());
                        aeroplane.setAirline(airline);
                        aeroplane.setCapacity(aeroplaneRequest.getCapacity());
                        aeroplane.setLuggageCapacity(aeroplaneRequest.getLuggageCapacity());

                        return aeroplaneRepository.save(aeroplane);
                    })
                    .orElseThrow(() -> new ResourceNotFoundException("Aeroplane with name already exists: " + request.getAeroplaneName()));
        }



    public Aeroplane updateAeroplane(AeroplaneRequest request, String aeroplaneName) {
            Aeroplane aeroplane = Optional.ofNullable(aeroplaneRepository.findByAeroplaneName(request.getAeroplaneName()))
                    .orElseThrow(() -> new ResourceNotFoundException("Aeroplane with name not found: " + request.getAeroplaneName()));
            //Airline Setting up
        Airline airline = airlineService.getAirlineByName(request.getAirlineName());
        aeroplane.setAirline(airline);

        //setting up aeroplane details
        aeroplane.setAeroplaneName(request.getAeroplaneName());
        aeroplane.setAeroplaneType(request.getAeroplaneType());
        aeroplane.setCapacity(request.getCapacity());
        aeroplane.setLuggageCapacity(request.getLuggageCapacity());
            return  aeroplaneRepository.save(aeroplane);
        }

    public boolean   existsByAeroplaneName(String aeroplaneName) {
        if (aeroplaneRepository.findByAeroplaneName(aeroplaneName) != null) {
            throw new AlreadyExistsException("Aeroplane with name already exists: " + aeroplaneName);
        }
        return false;
    }

        public AeroplaneResponseDto aeroplaneResponseDto(Aeroplane aeroplane){
            return modelMapper.map(aeroplane, AeroplaneResponseDto.class);
        }

}
