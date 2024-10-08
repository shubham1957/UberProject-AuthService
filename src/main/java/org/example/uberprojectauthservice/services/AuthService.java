package org.example.uberprojectauthservice.services;

import org.example.uberprojectauthservice.dtos.PassengerDto;
import org.example.uberprojectauthservice.dtos.PassengerSignupRequestDto;
import org.example.uberprojectauthservice.repositories.PassengerRepository;
import org.example.uberprojectentityservice.models.Passenger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for passenger signup and password encryption while signing-up
 */

@Service
public class AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PassengerRepository passengerRepository;

    public AuthService(PassengerRepository passengerRepository,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.passengerRepository=passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public PassengerDto signupPassenger (PassengerSignupRequestDto passengerSignupRequestDto){
        Passenger passenger = Passenger.builder()
                .name(passengerSignupRequestDto.getName())
                .email(passengerSignupRequestDto.getEmail())
                .password(bCryptPasswordEncoder.encode(passengerSignupRequestDto.getPassword()))
                .phoneNumber(passengerSignupRequestDto.getPhoneNumber())
                .build();

        Passenger newPassenger = passengerRepository.save(passenger);

        return PassengerDto.from(newPassenger);

    }
}
