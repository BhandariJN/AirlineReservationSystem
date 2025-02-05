package com.airlinereservationsystem.airlinesreservationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Airport {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true)
        private String code;  // IATA airport code (e.g., JFK, LAX)

        private String name;
        private String city;
        private String country;

        @JsonIgnore
        // Flights where this airport is the origin
        @OneToMany(mappedBy = "originAirport")  // Corrected to reference 'originAirport'
        private List<Flight> flightsAsOrigin;

        @JsonIgnore
        // Flights where this airport is the destination
        @OneToMany(mappedBy = "destinationAirport")  // Corrected to reference 'destinationAirport'
        private List<Flight> flightsAsDestination;

    }
