package com.fooddelivery.backend.Models.Request;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUp {
    private String username;
    private String password;
    private String firstname;
    private String surename;
    private double longitude;
    private double latitude;
    private String imageurl;
    
    public UserSignUp(String username, String password, String firstname, String surename) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.surename = surename;
    }
    
    
}
