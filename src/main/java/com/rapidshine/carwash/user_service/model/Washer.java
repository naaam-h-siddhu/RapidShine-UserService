package com.rapidshine.carwash.user_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rapidshine.carwash.user_service.dto.WasherDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "washer")
@Data
public class Washer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long washerId;
    private String name;
    private String email;
    @OneToOne
    @JoinColumn(name = "user_id",nullable = false,unique = true)
    @JsonBackReference
    private User user;
    private String phoneNumber;
    private String address;
    private boolean isAvailable;
    public Washer(String name,String email,String address,String phoneNumber,boolean isAvailable){
        this.name= name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isAvailable = isAvailable;
    }

}
