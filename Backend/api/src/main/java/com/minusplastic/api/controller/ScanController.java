package com.minusplastic.api.controller;

import com.minusplastic.api.User;
import com.minusplastic.api.Scan;
import com.minusplastic.api.ScanRepository;
import com.minusplastic.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/scans")
public class ScanController {

    @Autowired
    private ScanRepository scanRepository;

    @Autowired
    private UserRepository userRepository;

    public static class ScanRequest {
        public String barcode;
    }

    @PostMapping
    public ResponseEntity<?> recordScan(@RequestBody ScanRequest scanRequest, Principal principal) {
        User currentUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // --- THIS IS THE NEW DUPLICATE CHECK ---
        if (scanRepository.existsByBarcodeAndUser(scanRequest.barcode, currentUser)) {
            // Return a 409 Conflict status if the barcode is a duplicate
            return ResponseEntity.status(409).body(Map.of("message", "This bottle has already been scanned."));
        }

        Scan newScan = new Scan();
        newScan.setBarcode(scanRequest.barcode);
        newScan.setUser(currentUser);
        newScan.setTimestamp(LocalDateTime.now());

        scanRepository.save(newScan);

        long totalScans = scanRepository.countByUser(currentUser);
        return ResponseEntity.ok(Map.of("message", "Scan recorded successfully", "totalScans", totalScans));
    }
}