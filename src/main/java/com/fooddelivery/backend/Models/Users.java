package com.fooddelivery.backend.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fooddelivery.backend.Models.Enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Users")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "username cannot be blank")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotBlank(message = "password cannot be blank")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "firstname cannot be blank")
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @NotBlank(message = "surename cannot be blank")
    @Column(name = "surename", nullable = false)
    private String surename;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private List<Role> roles = new ArrayList<>();

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "imageurl")
    private String imageurl;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customer customer;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Courier courier;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Owner owner;
   

    public Users(String username, String password, String firstname, String surename) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.surename = surename;
        this.roles.add(Role.USER);
    }
    
     public Users(String username, String password, String firstname, String surename, Double latitude, Double longitude, String imageurl) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.surename = surename;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageurl = imageurl;
        this.roles.add(Role.USER);
    }

     public Users(String username, String password, String firstname, String surename, Double latitude, Double longitude) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.surename = surename;
        this.latitude = latitude;
        this.longitude = longitude;
        
    }

    @Override
    public String toString() {
        return "Users [id=" + id + ", username=" + username + ", password=" + password + ", firstname=" + firstname
                + ", surename=" + surename + ", roles=" + roles + ", longitude=" + longitude + ", latitude=" + latitude
                + ", imageurl=" + imageurl + "]";
    }
   

    
}

