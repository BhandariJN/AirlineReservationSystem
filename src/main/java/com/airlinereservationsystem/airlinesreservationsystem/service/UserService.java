package com.airlinereservationsystem.airlinesreservationsystem.service;

import com.airlinereservationsystem.airlinesreservationsystem.exception.ResourceNotFoundException;
import com.airlinereservationsystem.airlinesreservationsystem.model.Role;
import com.airlinereservationsystem.airlinesreservationsystem.model.User;
import com.airlinereservationsystem.airlinesreservationsystem.repository.RoleRepository;  // Make sure RoleRepository is imported
import com.airlinereservationsystem.airlinesreservationsystem.repository.UserRepository;
import com.airlinereservationsystem.airlinesreservationsystem.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;  // Inject RoleRepository

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User createUser(RegisterRequest request) {
        return Optional.of(request)
                .filter(registerRequest -> !userRepository.existsByEmail(registerRequest.getEmail()))
                .map(registerRequest -> {
                    User user = new User();
                    user.setEmail(registerRequest.getEmail());
                    user.setPassword(registerRequest.getPassword());
                    user.setFirstName(registerRequest.getFirstName());
                    user.setLastName(registerRequest.getLastName());

                    // Assign role
                    Role role = roleRepository.findByRoleName(registerRequest.getRole())
                            .orElse(roleRepository.save(new Role(registerRequest.getRole())));
                    user.setRoles(Set.of(role));

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User with email already exists: " + request.getEmail()));
    }


    public User getUserByUserName(String userName){
        return userRepository.findByEmail(userName);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id not found: " + userId));
    }

}
