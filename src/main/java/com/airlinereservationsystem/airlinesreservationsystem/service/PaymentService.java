package com.airlinereservationsystem.airlinesreservationsystem.service;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.BookingStatus;
import com.airlinereservationsystem.airlinesreservationsystem.Enum.PaymentStatus;
import com.airlinereservationsystem.airlinesreservationsystem.exception.AlreadyExistsException;
import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Booking;
import com.airlinereservationsystem.airlinesreservationsystem.model.Payment;
import com.airlinereservationsystem.airlinesreservationsystem.repository.BookingRepository;
import com.airlinereservationsystem.airlinesreservationsystem.repository.PaymentRepository;
import com.airlinereservationsystem.airlinesreservationsystem.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final FlightService flightService;
    private  final BookingService bookingService;
    private final BookingRepository bookingRepository;


    @Transactional
    public Payment processPayment(PaymentRequest paymentRequest) {

        Booking booking = bookingService.getBookingById(paymentRequest.getBookingId());
       if (booking.getStatus() == BookingStatus.BOOKED) {
            throw new AlreadyExistsException("Booking is already confirmed");
        }

        // Check if payment amount is equal to booking amount
        if (paymentRequest.getAmount().compareTo(booking.getAmount()) != 0) {
            throw new ResourceNotFoundException("Payment amount does not match booking amount");
        }



        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(paymentRequest.getMethod());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PAID);
        payment.setBooking(booking);
        payment = paymentRepository.save(payment);

        // Update booking status upon successful payment
        booking.setStatus(BookingStatus.BOOKED);
        bookingRepository.save(booking);
        return payment;
    }



}
