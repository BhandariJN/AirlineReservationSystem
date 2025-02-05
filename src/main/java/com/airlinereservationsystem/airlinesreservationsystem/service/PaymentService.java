package com.airlinereservationsystem.airlinesreservationsystem.service;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.BookingStatus;
import com.airlinereservationsystem.airlinesreservationsystem.Enum.PaymentStatus;
import com.airlinereservationsystem.airlinesreservationsystem.model.Booking;
import com.airlinereservationsystem.airlinesreservationsystem.model.Flight;
import com.airlinereservationsystem.airlinesreservationsystem.model.Payment;
import com.airlinereservationsystem.airlinesreservationsystem.model.Reservation;
import com.airlinereservationsystem.airlinesreservationsystem.repository.BookingRepository;
import com.airlinereservationsystem.airlinesreservationsystem.repository.PaymentRepository;
import com.airlinereservationsystem.airlinesreservationsystem.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
            throw new RuntimeException("Booking is already confirmed");
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
