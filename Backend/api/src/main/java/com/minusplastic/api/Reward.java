package com.minusplastic.api;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rewards")
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int requiredScans; // The number of scans needed to earn this reward
}