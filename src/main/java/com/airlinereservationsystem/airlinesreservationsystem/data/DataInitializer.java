package com.airlinereservationsystem.airlinesreservationsystem.data;

import com.airlinereservationsystem.airlinesreservationsystem.model.Role;
import com.airlinereservationsystem.airlinesreservationsystem.model.User;
import com.airlinereservationsystem.airlinesreservationsystem.repository.RoleRepository;
import com.airlinereservationsystem.airlinesreservationsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;
@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
  //  private final PasswordEncoder passwordEncoder = new PasswordEncoder
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> roles = Set.of("ROLE_USER", "ROLE_ADMIN");
        createRoleIfNotExist(roles);
        createDefaultUserIfNotExist();
        createDefaultAdminIfNotExist();
    }

    private void createDefaultUserIfNotExist() {
        // Fetch the role for "ROLE_USER"
        Role userRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Role ROLE_USER not found in the database"));

        // Loop to create default users
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }

            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
           // user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);

            System.out.println("Default user " + i + " created successfully");
        }
    }

    private void createDefaultAdminIfNotExist() {
        // Fetch the role for "ROLE_ADMIN"
        Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN")
                .orElseThrow(() -> new IllegalStateException("Role ROLE_ADMIN not found in the database"));

        // Loop to create default admin users
        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }

            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
         //   user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);

            System.out.println("Default admin user " + i + " created successfully");
        }
    }

    private void createRoleIfNotExist(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByRoleName(role).isEmpty()) // Check if role doesn't exist
                .map(Role::new) // Create new Role object
                .forEach(roleRepository::save); // Save the new Role
    }
}

