package org.example.uberprojectauthservice.repositories;

import org.example.uberprojectentityservice.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver,Long> {

    Optional<Driver> findByAadharCard(String aadharNumber);
}
