package com.airlinereservationsystem.airlinesreservationsystem.service;

import com.airlinereservationsystem.airlinesreservationsystem.exception.AlreadyExistsException;
import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Role;
import com.airlinereservationsystem.airlinesreservationsystem.model.User;
import com.airlinereservationsystem.airlinesreservationsystem.repository.RoleRepository;  // Make sure RoleRepository is imported
import com.airlinereservationsystem.airlinesreservationsystem.repository.UserRepository;
import com.airlinereservationsystem.airlinesreservationsystem.request.RegisterRequest;
import com.airlinereservationsystem.airlinesreservationsystem.request.UpdateUserRequest;
import com.airlinereservationsystem.airlinesreservationsystem.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    public User createUser(RegisterRequest request) {
        return Optional.of(request).filter(user->!userRepository.existsByEmail(request.getEmail()))
                .map(req->{
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepository.save(user);
                }).orElseThrow(()->new AlreadyExistsException(request.getEmail()+ "email already exist!"));
    }


    public User getUserByUserName(String userName){
        return userRepository.findByEmail(userName);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id not found: " + userId));
    }

    public User updateUser(UpdateUserRequest request, Long id) {
        return userRepository.findById(id).map(existingUser->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return  userRepository.save(existingUser);
        }).orElseThrow(()->new ResourceNotFoundException("User Not Found!"));
    }


    public void deleteUser(Long id) {

        userRepository.findById(id)
                .ifPresentOrElse(userRepository::delete, ()-> {throw new ResourceNotFoundException("User Not Found!");
                });
    }

    public UserResponse convertToUserDto(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public User getAuthencatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
