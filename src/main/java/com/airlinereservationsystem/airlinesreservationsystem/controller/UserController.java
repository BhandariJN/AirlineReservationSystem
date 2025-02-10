package com.airlinereservationsystem.airlinesreservationsystem.controller;


import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.User;
import com.airlinereservationsystem.airlinesreservationsystem.request.RegisterRequest;
import com.airlinereservationsystem.airlinesreservationsystem.request.UpdateUserRequest;
import com.airlinereservationsystem.airlinesreservationsystem.response.ApiResponse;
import com.airlinereservationsystem.airlinesreservationsystem.response.UserResponse;
import com.airlinereservationsystem.airlinesreservationsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<ApiResponse> getUserByUserId(@PathVariable Long userId){
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(new ApiResponse("success", user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody RegisterRequest request){

        try {
            User user = userService.createUser(request);
            UserResponse responseDto = userService.convertToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("User Create Success!", responseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage (), null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId){
        try {
            User user = userService.updateUser(request, userId);
            UserResponse responseDto = userService.convertToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("User Update Success!", responseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }


    }


    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){

        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User Delete Success!", userId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
