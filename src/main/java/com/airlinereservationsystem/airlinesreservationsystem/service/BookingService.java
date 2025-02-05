package com.airlinereservationsystem.airlinesreservationsystem.service;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.BookingStatus;
import com.airlinereservationsystem.airlinesreservationsystem.Enum.ReservationStatus;
import com.airlinereservationsystem.airlinesreservationsystem.Enum.SeatStatus;
import com.airlinereservationsystem.airlinesreservationsystem.model.*;
import com.airlinereservationsystem.airlinesreservationsystem.repository.BookingRepository;
import com.airlinereservationsystem.airlinesreservationsystem.repository.SeatRepository;
import com.airlinereservationsystem.airlinesreservationsystem.response.BookingResponse;
import com.airlinereservationsystem.airlinesreservationsystem.response.ReservationResponseUser;
import com.airlinereservationsystem.airlinesreservationsystem.response.SeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookingService {
private final BookingRepository bookingRepository;
private final ReservationService reservationService;
private final FlightService flightService;
private final UserService userService;
    private final SeatRepository seatRepository;


    public Booking createBooking(Long reservationId) {
        // Fetch reservation
        Reservation reservation = reservationService.getReservationById(reservationId);

        // Get user from reservation (not hardcoded)

        User user = userService.getUserById(1L);

        // Create booking
        Booking booking = new Booking();
        booking.setFlight(reservation.getFlight());
        booking.setUser(user);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(BookingStatus.INITIATED);
        booking.setAmount(reservation.getTotalPrice());

        // Save booking first before assigning to seats
        booking = bookingRepository.save(booking);

        // Move reserved seats to booking
        List<Seat> reservedSeats = reservation.getSeats();
        for (Seat seat : reservedSeats) {
            seat.setSeatStatus(SeatStatus.BOOKED);
            seat.setBooking(booking);
            seat.setReservation(null); // Clear reservation reference
        }

        seatRepository.saveAll(reservedSeats); // Save updated seats
        booking.setSeats(reservedSeats); // Assign seats to booking

        reservationService.deleteReservation(reservationId); // Delete reservation

        return booking;
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public List<Booking> getBookingByUserId(Long userId) {
        return bookingRepository.findByUser_Id(userId);
    }

    public BookingResponse toBookingResponse(Booking booking) {

        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setTotalPrice(booking.getAmount());
        response.setStatus(booking.getStatus());
        response.setBookingTime(booking.getBookingDate());
        response.setSeats(booking.getSeats().stream().map(Seat::getSeatNumber).toList());
        response.setAirlineName(booking.getFlight().getAirline().getAirlineName());
        response.setDepartureAirport(booking.getFlight().getOriginAirport().getName());
        response.setArrivalTime(booking.getFlight().getArrivalTime());
        response.setArrivalAirport(booking.getFlight().getDestinationAirport().getName());
        response.setDepartureTime(booking.getFlight().getDepartureTime());
        response.setFlightStatus(booking.getFlight().getStatus().toString());
        response.setUserName(booking.getUser().getEmail());

        return response;
    }

}
