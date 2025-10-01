package com.minusplastic.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanRepository extends JpaRepository<Scan, Long> {
    // We can add methods to count scans per user later
    long countByUser(User user);

    boolean existsByBarcodeAndUser(String barcode, User user);
}