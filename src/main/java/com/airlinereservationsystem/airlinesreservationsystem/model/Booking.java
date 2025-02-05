package com.airlinereservationsystem.airlinesreservationsystem.model;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    private LocalDateTime bookingDate;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private BigDecimal amount; // Amount to be paid


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Assuming you have a User entity representing the customer

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;  // A booking is related to one flight

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Seat> seats; // Booking has seats associated

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;  // A booking has payments

}
