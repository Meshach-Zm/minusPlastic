package com.minusplastic.api;

import jakarta.persistence.*;
import lombok.Data;

@Data // Lombok annotation to automatically create getters, setters, etc.
@Entity // Marks this class as a JPA entity (a database table)
@Table(name = "users") // Specifies the table name in the database
public class User {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
    private Long id;

    private String fullName;

    @Column(unique = true) // Ensures every email is unique in the database
    private String email;

    private String password;
}