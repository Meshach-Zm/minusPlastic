package com.minusplastic.api.controller;

import com.minusplastic.api.User;
import com.minusplastic.api.ScanRepository;
import com.minusplastic.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScanRepository scanRepository;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        // Find the full User object from the database
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Get the scan count for that user
        long count = scanRepository.countByUser(user);

        // Create and return our custom response object
        UserResponse response = new UserResponse(user.getEmail(), user.getFullName(), count);
        return ResponseEntity.ok(response);
    }
}