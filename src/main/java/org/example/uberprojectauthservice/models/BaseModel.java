package org.example.uberprojectauthservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

//Auditing allows you to automatically capture and store metadata such as the creation date,
// last modification date, and other auditing details like the user who created or modified the entity.
@EntityListeners(AuditingEntityListener.class)
//To make use of @EntityListeners(AuditingEntityListener.class), you need to enable auditing in your Spring Boot application:
//Add @EnableJpaAuditing to a Main Configuration Class
@Getter
@Setter
@MappedSuperclass //no table can be created for parent class
// each child class will have a separate table with its own attribute and parent class attributes
// class has been made abstract, so that no object can be created out of it.
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @CreatedDate
    // this will tell spring that only handel it for object creation, and you don't need to pass it while creating object
    protected Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate // this will tell spring that only handel it for object update, and you don't hve to pass it
    protected Date updatedAt;
}