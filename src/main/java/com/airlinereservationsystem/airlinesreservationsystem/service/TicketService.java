package com.airlinereservationsystem.airlinesreservationsystem.service;

import com.airlinereservationsystem.airlinesreservationsystem.Enum.PaymentStatus;
import com.airlinereservationsystem.airlinesreservationsystem.model.Booking;
import com.airlinereservationsystem.airlinesreservationsystem.response.BookingResponse;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class TicketService {
private final BookingService bookingService;
    public ResponseEntity<byte[]> generateTicket(Long bookingId) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Booking originalbooking = bookingValidation(bookingId);
            BookingResponse booking = bookingService.toBookingResponse(originalbooking);
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Sample ticket content
            document.add(new Paragraph("BOOKING REF: " + bookingId));
            document.add(new Paragraph("ITINERARY Issued at: "+booking.getBookingTime()));
            document.add(new Paragraph("ITINERARY PREPARED FOR:"));
            document.add(new Paragraph(booking.getUserName()));
            document.add(new Paragraph("FLIGHT DETAILS:"));
            document.add(new Paragraph(
                    "FLIGHT: "+booking.getAirlineName()
                            +" | From "+booking.getDepartureAirport()
                            +" at "+booking.getDepartureTime()
                            +" | To "+booking.getArrivalAirport()
                            +" at "+booking.getArrivalTime()));
            document.add(new Paragraph("Seat Numbers: "+booking.getSeats()));

            // Close the document
            document.close();

            // Get the PDF bytes
            byte[] pdfBytes = outputStream.toByteArray();

            // Return the PDF as a response
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ticket.pdf")
                    .body(pdfBytes);
        } catch (Exception e) {
            // Log the error (optional)

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body((("Error generating ticket: " +e.getMessage()).getBytes()));
        }
    }

    public Booking bookingValidation(Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        if(booking.getPayment()==null ||booking.getPayment().getStatus()!=PaymentStatus.PAID){
            throw new RuntimeException("Please pay for get ticket first");
        }
        return booking;
    }

}
