package com.airlinereservationsystem.airlinesreservationsystem.controller;

import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Booking;
import com.airlinereservationsystem.airlinesreservationsystem.response.ApiResponse;
import com.airlinereservationsystem.airlinesreservationsystem.response.BookingResponse;
import com.airlinereservationsystem.airlinesreservationsystem.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/booking")
public class BookingController {

    private final BookingService bookingService;


    @PostMapping("/{reservationId}/book")
    public ResponseEntity<ApiResponse> createBooking(@PathVariable Long reservationId) {
        try {
            Booking booking = bookingService.createBooking(reservationId);
            BookingResponse response = bookingService.toBookingResponse(booking);

            return ResponseEntity.ok(new ApiResponse("Booking created successfully please proceed for payment", response));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse> getBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        BookingResponse response = bookingService.toBookingResponse(booking);
        return ResponseEntity.ok(new ApiResponse("Booking Found!", response));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getBookingByUserId(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getBookingByUserId(userId);
        List<BookingResponse> responses = bookings.stream().map(bookingService::toBookingResponse).toList();
        return ResponseEntity.ok(new ApiResponse("Booking Found", responses));
    }


}
