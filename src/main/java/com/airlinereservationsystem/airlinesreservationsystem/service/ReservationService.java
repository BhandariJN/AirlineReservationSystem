package com.airlinereservationsystem.airlinesreservationsystem.service;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.ReservationStatus;
import com.airlinereservationsystem.airlinesreservationsystem.Enum.SeatClass;
import com.airlinereservationsystem.airlinesreservationsystem.Enum.SeatStatus;
import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Flight;
import com.airlinereservationsystem.airlinesreservationsystem.model.Reservation;
import com.airlinereservationsystem.airlinesreservationsystem.model.Seat;
import com.airlinereservationsystem.airlinesreservationsystem.model.User;
import com.airlinereservationsystem.airlinesreservationsystem.repository.FlightRepository;
import com.airlinereservationsystem.airlinesreservationsystem.repository.ReservationRepository;
import com.airlinereservationsystem.airlinesreservationsystem.repository.SeatRepository;
import com.airlinereservationsystem.airlinesreservationsystem.request.ReservationRequest;
import com.airlinereservationsystem.airlinesreservationsystem.response.ReservationResponse;
import com.airlinereservationsystem.airlinesreservationsystem.response.ReservationResponseUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private  final  ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final FlightService flightService;
    private final SeatService seatService;
    private final SeatRepository seatRepository;
    private final ModelMapper mapper;
    private final UserService userService;

    public List<Reservation> getAllReservationByAirlineName(String airlineName) {
        return    Optional.ofNullable(reservationRepository.findByFlight_Airline_AirlineName(airlineName))
                .orElseThrow(()-> new ResourceNotFoundException("No reservation found for airline: "+airlineName));

    }

    public List<Reservation> getAllReservationByUserName(String userName) {
        return    Optional.ofNullable(reservationRepository.findByUser_email(userName))
                .orElseThrow(()-> new ResourceNotFoundException("No reservation found for user: "+userName));

    }

    public void deleteReservation(Long id) {
        reservationRepository.findById(id).ifPresent(reservationRepository::delete);
    }

    @Transactional
    public Reservation makeReservation(ReservationRequest reservationRequest) {
        User user =userService.getAuthencatedUser();
        reservationRequest.setUserId(user.getId());
        //function to check already existes reservation of same user and same flight and same seatclass
        if (reservationExists(reservationRequest.getUserId(), reservationRequest.getFlightId(), reservationRequest.getSeatClass())) {
            throw new ResourceNotFoundException("Reservation already exists for the user on the flight! You Can Update the reservation");
        }


        // Fetch flight and user details
        Flight flight = Optional.ofNullable(flightService.getFlightByFlightNumber(reservationRequest.getFlightId()))
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));



        validateSeatAvailability(reservationRequest);
        // Fetch seats
        List<Seat> seats = fetchAvailableSeats(reservationRequest);

        // Create reservation
        Reservation reservation = createReservation(reservationRequest, flight, user, seats);

        // Update seat statuses
        updateSeatStatuses(seats, reservation);

        return reservation;
    }

    private void validateSeatAvailability(ReservationRequest request) {
        if (!checkIfSeatsAreAvailable(request.getFlightId(), request.getSeatClass(), request.getSeatNumbers())) {
            throw new ResourceNotFoundException("Seats are not available");
        }
    }

    private List<Seat> fetchAvailableSeats(ReservationRequest request) {
        List<Seat> seats = seatRepository.findBySeatNumberIn(request.getSeatNumbers());
        if (seats.size() != request.getSeatNumbers().size()) {
            throw new ResourceNotFoundException("Some seats are unavailable. Try other seats.");
        }
        return seats;
    }

    private Reservation createReservation(ReservationRequest request, Flight flight, User user, List<Seat> seats) {
        reservationExists(user.getId(), flight.getFlightNumber(), request.getSeatClass());
        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setUser(user);
        reservation.setSeatClass(request.getSeatClass());
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setBookingTime(LocalDateTime.now());
        reservation.setSeats(seats);
        reservation.setTotalPrice(calculateTotalPrice(request.getSeatNumbers()));

        return reservationRepository.save(reservation);
    }

    private void updateSeatStatuses(List<Seat> seats, Reservation reservation) {
        seats.forEach(seat -> {
            seat.setSeatStatus(SeatStatus.RESERVED);
            seat.setReservation(reservation);
        });
        seatRepository.saveAll(seats); // Batch update
    }



    public boolean checkIfSeatsAreAvailable(String flightId, SeatClass seatClass, Set<String> seatNumbers) {
        Long id = Optional.ofNullable(flightService.getFlightByFlightNumber(flightId).getId()).orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        return flightRepository.findById(id)
                .map(flight -> {
                    // Get available seats of the given class
                    Set<String> availableSeats = flight.getAeroplane().getSeats().stream()
                            .filter(seat -> seat.getSeatClass().equals(seatClass))
                            .filter(seat -> seat.getSeatStatus() == SeatStatus.AVAILABLE) // Check status
                            .map(Seat::getSeatNumber)
                            .collect(Collectors.toSet());

                    // Check if all requested seat numbers exist in the available set
                    return availableSeats.containsAll(seatNumbers);
                })
                .orElse(false);
    }


    public BigDecimal calculateTotalPrice(Set<String> seatNumbers) {


        List<Seat> seats = seatNumbers.stream().map(seatService::getSeatBySeatNumber).toList();
        return seats.stream().map(Seat::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Reservation> getReservationByUserId(Long userId) {
        return Optional.ofNullable(reservationRepository.findByUser_Id(userId))
                .orElseThrow(() -> new ResourceNotFoundException("No reservation found for user with ID: " + userId));
    }

    private boolean reservationExists(Long userId, String flightId, SeatClass seatClass) {
        return reservationRepository.existsByUserIdAndFlightIdAndSeatClass(userId, flightId, seatClass);
    }


    public ReservationResponse toResponse(Reservation reservation) {

    ReservationResponse response = new ReservationResponse();
        response.setReservationId(reservation.getId());
        response.setSeatClass(reservation.getSeatClass());
        response.setTotalPrice(reservation.getTotalPrice());
        response.setStatus(reservation.getStatus());
        response.setBookingTime(reservation.getBookingTime());
        response.setSeats(reservation.getSeats().size());
        response.setAirlineName(reservation.getFlight().getAirline().getAirlineName());
        response.setDepartureAirport(reservation.getFlight().getOriginAirport().getName());
        response.setArrivalTime(reservation.getFlight().getArrivalTime());
        response.setArrivalAirport(reservation.getFlight().getDestinationAirport().getName());
        response.setDepartureTime(reservation.getFlight().getDepartureTime());
        response.setFlightStatus(reservation.getFlight().getStatus().toString());
        response.setUserName(reservation.getUser().getEmail());


        return response;
    }

    public ReservationResponseUser toResponseUser(Reservation reservation) {

        ReservationResponseUser response = new ReservationResponseUser();
        response.setReservationId(reservation.getId());
        response.setSeatClass(reservation.getSeatClass());
        response.setTotalPrice(reservation.getTotalPrice());
        response.setStatus(reservation.getStatus());
        response.setBookingTime(reservation.getBookingTime());
        response.setSeats(reservation.getSeats().stream().map(Seat::getSeatNumber).toList());
        response.setAirlineName(reservation.getFlight().getAirline().getAirlineName());
        response.setDepartureAirport(reservation.getFlight().getOriginAirport().getName());
        response.setArrivalTime(reservation.getFlight().getArrivalTime());
        response.setArrivalAirport(reservation.getFlight().getDestinationAirport().getName());
        response.setDepartureTime(reservation.getFlight().getDepartureTime());
        response.setFlightStatus(reservation.getFlight().getStatus().toString());
        response.setUserName(reservation.getUser().getEmail());


        return response;
    }

    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

    }

    @Transactional
    @Scheduled(cron = "0 0/15 * * * ?") // Runs every 15 minutes
    public void reservationCancel() {
        LocalDateTime time = LocalDateTime.now().minusMinutes(15);

        List<Reservation> reservations = reservationRepository.findByStatus(ReservationStatus.CONFIRMED);
        if (reservations.isEmpty()) {
            return;
        }
        System.out.println(reservations.size() + " reservations.");

        // Filter and process only reservations that match the condition
        reservations.stream()
                .filter(reservation -> reservation.getBookingTime().isBefore(time)) // Apply condition
                .forEach(reservation -> {
                    // Update seats to available
                    reservation.getSeats().forEach(seat -> {
                        seat.setSeatStatus(SeatStatus.AVAILABLE);
                        seatRepository.save(seat); // Save seat status update
                    });
                    // Delete the reservation after updating seats
                    reservationRepository.delete(reservation);
                });

        System.out.println("Cancelled " + reservations.size() + " reservations.");
    }

}
