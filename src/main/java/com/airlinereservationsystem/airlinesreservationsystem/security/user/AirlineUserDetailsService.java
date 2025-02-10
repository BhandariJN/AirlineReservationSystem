package com.airlinereservationsystem.airlinesreservationsystem.security.user;

import com.airlinereservationsystem.airlinesreservationsystem.model.User;
import com.airlinereservationsystem.airlinesreservationsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AirlineUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.of(userRepository.findByEmail(email)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return AirlineUserDetails.buildUserDetails(user);
    }
}
