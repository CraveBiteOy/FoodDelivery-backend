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

@Entity(name = "Dish")
@Table(name = "dish")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "name cannot be blank")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "description cannot be blank")
    @Column(name = "description", nullable = false)
    private String description;

    @Min(value = 0, message = "price must be higher than 0")
    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "availability")
    private boolean availability;

    @Column(name = "imageurl")
    private String imageurl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;


    public Dish( String name, String description, double price, String imageurl,
            Restaurant restaurant) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageurl = imageurl;
        this.restaurant = restaurant;
        this.availability = true;
    }

    public Dish( String name, String description, double price,
            Restaurant restaurant) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
        this.availability = true;
    }

    

    public Dish( String name,String description, double price, boolean availability,
            String imageurl, Restaurant restaurant) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.imageurl = imageurl;
        this.restaurant = restaurant;
    }



    public boolean getAvailability() {
        return this.availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
    
}
