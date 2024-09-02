package org.example.uberprojectauthservice.dtos;


import lombok.*;
import org.example.uberprojectentityservice.models.Passenger;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// for sending response after signup
public class PassengerDto {

    private String id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private Date createdAt;

    public  static  PassengerDto from (Passenger p){

        return PassengerDto.builder()
                .id(p.getId().toString())
                .name(p.getName())
                .email(p.getEmail())
                .password(p.getPassword())
                .phoneNumber(p.getPhoneNumber())
                .createdAt(p.getCreatedAt())
                .build();

    }
}
