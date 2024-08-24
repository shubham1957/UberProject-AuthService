package org.example.uberprojectauthservice.dtos;


import lombok.*;

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
}
