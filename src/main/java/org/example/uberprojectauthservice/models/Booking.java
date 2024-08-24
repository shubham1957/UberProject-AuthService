package org.example.uberprojectauthservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking extends  BaseModel{


    @Enumerated(value = EnumType.STRING)
    private BookingStatus bookingStatus;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endDate;

    private Long totalDistance;

    @ManyToOne (fetch = FetchType.LAZY)
    private Driver driver; //Many Booking belongs to a driver and a driver belongs to many booking

    @ManyToOne(fetch = FetchType.LAZY)
    private Passenger passenger; //Many Booking belongs to a person


}
