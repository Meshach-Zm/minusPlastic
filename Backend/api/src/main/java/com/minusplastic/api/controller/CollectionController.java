package com.minusplastic.api.controller;

import com.minusplastic.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScanRepository scanRepository;
    @Autowired
    private RewardRepository rewardRepository;
    @Autowired
    private CollectionRepository collectionRepository;

    public static class ClaimRequest {
        public Long rewardId;
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestCollection(@RequestBody ClaimRequest claimRequest, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Reward reward = rewardRepository.findById(claimRequest.rewardId)
                .orElseThrow(() -> new IllegalArgumentException("Reward not found"));

        long userScanCount = scanRepository.countByUser(user);

        // Verify that the user has enough scans to claim this reward
        if (userScanCount < reward.getRequiredScans()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Not enough scans to claim this reward."));
        }

        // TODO: Add logic here to prevent claiming the same reward twice.

        Collection newCollection = new Collection();
        newCollection.setUser(user);
        newCollection.setReward(reward);
        newCollection.setStatus(CollectionStatus.PENDING);
        newCollection.setRequestedAt(LocalDateTime.now());

        collectionRepository.save(newCollection);

        return ResponseEntity.ok(Map.of("message", "Collection requested successfully! An officer will be in touch."));
    }
}