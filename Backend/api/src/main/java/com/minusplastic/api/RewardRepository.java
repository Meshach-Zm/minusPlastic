package com.minusplastic.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {

    // Find all rewards, ordered by the number of scans required
    List<Reward> findAllByOrderByRequiredScansAsc();
}