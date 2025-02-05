package com.airlinereservationsystem.airlinesreservationsystem.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class SeatRequest {
    private String aeroplaneName;

    private Map<String, SeatInfo> seatTypes;

    @Data
    public static class SeatInfo {
        private int row;
        private int column;
        private BigDecimal price;
    }
}
