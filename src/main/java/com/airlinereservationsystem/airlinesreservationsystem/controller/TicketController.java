package com.airlinereservationsystem.airlinesreservationsystem.controller;



import com.airlinereservationsystem.airlinesreservationsystem.service.TicketService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("${api.prefix}/tickets")
@RequiredArgsConstructor
public class TicketController {
   private final TicketService ticketService;

    @GetMapping("/{bookingId}")
    public ResponseEntity<byte[]> generateTicket(@PathVariable Long bookingId) {
        return ticketService.generateTicket(bookingId);
    }
}