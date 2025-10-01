package com.minusplastic.api;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marks this as a Spring repository bean
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // JpaRepository gives us methods like save(), findById(), findAll(), etc. for
    // free!
    // We can add custom query methods here later.
}