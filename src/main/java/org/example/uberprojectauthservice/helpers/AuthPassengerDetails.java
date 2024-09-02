package org.example.uberprojectauthservice.helpers;

import org.example.uberprojectentityservice.models.Passenger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * This class is needed because spring security does the authentication on "UserDetails" polymorphic type object
 * so wee need to convert the passenger object to UserDetail type of object for passenger authentication
 */

public class AuthPassengerDetails extends Passenger implements UserDetails {

    private final String username;
    private final String password;

    public AuthPassengerDetails (Passenger passenger){
        this.username=passenger.getEmail();
        this.password=passenger.getPassword();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    // Below setup methods are not such of a concern.
    // Since "UserDetails" interface consists these method so we need to implement them
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
