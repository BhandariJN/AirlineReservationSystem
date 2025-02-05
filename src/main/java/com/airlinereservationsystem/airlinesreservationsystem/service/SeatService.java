package com.airlinereservationsystem.airlinesreservationsystem.service;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.SeatClass;
import com.airlinereservationsystem.airlinesreservationsystem.Enum.SeatStatus;
import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Aeroplane;
import com.airlinereservationsystem.airlinesreservationsystem.model.Seat;
import com.airlinereservationsystem.airlinesreservationsystem.repository.SeatRepository;
import com.airlinereservationsystem.airlinesreservationsystem.request.SeatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service

public class SeatService {
    private final SeatRepository seatRepository;
    private final AeroplaneService aeroplaneService;


    public Set<Seat> getSeatsOfAeroplane(String aeroplaneName) {
        return Optional.ofNullable(seatRepository.findByAeroplane_AeroplaneName(aeroplaneName))
                .orElseThrow(() -> new ResourceNotFoundException("No seats found for aeroplane"));
    }

    public List<Seat> AddSeat(SeatRequest request){
        Aeroplane aeroplane = aeroplaneService.getAeroplaneByName(request.getAeroplaneName());
        Set<Seat> seats = new HashSet<>();

        request.getSeatTypes().forEach((seatClass, seatInfo) -> {
            generateSeats(seatInfo, seatClass, aeroplane, seats);
        });

        return seatRepository.saveAll(seats);
    }

    public Seat updateSeat(String seatName, SeatRequest request) {
     return null;
    }

    public void DeleteSeat(String seatName) {
        Seat seat = getSeatBySeatNumber(seatName);
        seatRepository.delete(seat);
    }

    public Seat getSeatBySeatNumber(String seatName) {
        return  Optional.ofNullable(seatRepository.findBySeatNumber(seatName))
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
    }

    private void generateSeats(SeatRequest.SeatInfo seatInfo, String seatClass, Aeroplane aeroplane, Set<Seat> seats) {
        for (int column = 1; column <= seatInfo.getColumn(); column++) {
            String seatColName = String.valueOf((char) ('A' + column - 1)); // 'A' + (row - 1) to start from 'A'
            for (int row = 1; row <= seatInfo.getRow(); row++) {
                Seat seat = new Seat();

                // Convert row number to letter (1 = A, 2 = B, etc.)

                // Create the seat name combining the row letter and column number
                String seatNumber = seatClass.substring(0,1).toUpperCase()+"-"+ seatColName + row;
                //System.out.println(seatNumber);


                seat.setSeatClass(SeatClass.valueOf(seatClass)); // Store seat class (Business, Economy, etc.)
                seat.setSeatNumber(seatNumber); // Store the generated seat name (e.g., A1, B2, etc.)
                seat.setPrice(seatInfo.getPrice()); // Set the price for the seat
                seat.setAeroplane(aeroplane); // Link seat to aeroplane
                seat.setSeatStatus(SeatStatus.AVAILABLE); // Set seat status to available
                seats.add(seat);
            }
        }
    }


    public void deleteAllSeatsOfAeroplane(String aeroplaneName) {
        Set<Seat> seats = getSeatsOfAeroplane(aeroplaneName);
        seatRepository.deleteAll(seats);
    }

    public List<Seat> getSeatbySeatClass(String aeroplaneName, SeatClass seatclass) {

        return Optional.ofNullable(seatRepository.findByAeroplane_AeroplaneNameAndSeatClass(aeroplaneName, seatclass))
                .orElseThrow(() -> new ResourceNotFoundException("No seats found for aeroplane"));
    }
}
