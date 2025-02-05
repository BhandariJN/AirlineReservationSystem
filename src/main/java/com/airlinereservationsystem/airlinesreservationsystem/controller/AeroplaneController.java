package com.airlinereservationsystem.airlinesreservationsystem.controller;

import com.airlinereservationsystem.airlinesreservationsystem.response.AeroplaneResponseDto;
import com.airlinereservationsystem.airlinesreservationsystem.exception.AlreadyExistsException;
import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Aeroplane;
import com.airlinereservationsystem.airlinesreservationsystem.request.AeroplaneRequest;
import com.airlinereservationsystem.airlinesreservationsystem.response.ApiResponse;
import com.airlinereservationsystem.airlinesreservationsystem.service.AeroplaneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aeroplane")
public class AeroplaneController {
    private final AeroplaneService aeroplaneService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAeroplane(@RequestBody AeroplaneRequest request) {

        try {
            Aeroplane aeroplane = aeroplaneService.addAeroplane(request);
            AeroplaneResponseDto aeroplaneResponseDto = aeroplaneService.aeroplaneResponseDto(aeroplane);
            return ResponseEntity.ok(new ApiResponse("Aeroplane added successfully", aeroplaneResponseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{aeroplaneName}")
    public ResponseEntity<ApiResponse> deleteAeroplane(@PathVariable String aeroplaneName) {
        try {
            aeroplaneService.deleteAeroplane(aeroplaneName);
            return ResponseEntity.ok(new ApiResponse("Aeroplane deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{aeroplaneName}")
    public ResponseEntity<ApiResponse> updateAeroplane(@RequestBody AeroplaneRequest request, @PathVariable String aeroplaneName) {
             try {
                 Aeroplane aeroplane = aeroplaneService.updateAeroplane(request, aeroplaneName);
                 AeroplaneResponseDto aeroplaneResponseDto = aeroplaneService.aeroplaneResponseDto(aeroplane);
                 return ResponseEntity.ok(new ApiResponse("Aeroplane updated successfully", aeroplaneResponseDto));
             } catch (ResourceNotFoundException e) {
                 return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
             }
    }
    @GetMapping("/get/{airlineName}")
    public ResponseEntity<ApiResponse> getAllAeroplaneByAirlineName(@PathVariable String airlineName) {
            try {
                List<Aeroplane> aeroplaneList = aeroplaneService.getAllAeroplaneByAirlineName(airlineName);
                List<AeroplaneResponseDto> aeroplaneResponseDtoList = aeroplaneList.stream().map(aeroplaneService::aeroplaneResponseDto).toList();
                return ResponseEntity.ok(new ApiResponse("Aeroplane fetched successfully", aeroplaneResponseDtoList));
             }
            catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
         }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
            }
    }
}
