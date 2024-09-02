package org.example.uberprojectauthservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.uberprojectentityservice.models.Passenger;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Optional<Passenger> findPassengerByEmail(String email);
}
