package com.fooddelivery.backend.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fooddelivery.backend.Models.Enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Restaurant")
@Table(name = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "name cannot be blank")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotBlank(message = "address cannot be blank")
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank(message = "zipcode cannot be blank")
    @Column(name = "zipcode",  nullable = false)
    private String zipcode;

    @NotBlank(message = "city cannot be blank")
    @Column(name = "city",  nullable = false)
    private String city;

    @Min(value = 0, message = "rating must be higher than 0")
    @Column(name = "rating")
    private Double rating;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "imageurl")
    private String imageurl;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Owner owner;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Dish> dishes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Basket> baskets = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;


    public Restaurant( String name, String address, String zipcode, String city, String imageurl, Owner owner) {
        this.name = name;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.imageurl = imageurl;
        this.owner = owner;
    }

     public Restaurant( String name, String address, String zipcode, String city, String imageurl, Owner owner, double latitude, double longitude, Double rating) {
        this.name = name;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.imageurl = imageurl;
        this.owner = owner;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
    }

    
}
