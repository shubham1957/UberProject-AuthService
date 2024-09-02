package org.example.uberprojectauthservice.services;

import org.example.uberprojectauthservice.helpers.AuthPassengerDetails;
import org.example.uberprojectauthservice.repositories.PassengerRepository;
import org.example.uberprojectentityservice.models.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is responsible for loading the user in the form UserDetail object for Auth
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PassengerRepository passengerRepository;

    /**
     * The method returns a UserDetails object that Spring Security uses for authentication and authorization.
     * @param email
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(email);

        if(passenger.isPresent()){
            return new AuthPassengerDetails(passenger.get());
        }
        else{
            throw new UsernameNotFoundException("Cannot find the passenger with email "+email);
        }
    }
}
