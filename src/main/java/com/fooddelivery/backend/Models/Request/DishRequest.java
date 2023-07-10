package com.fooddelivery.backend.Models.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishRequest {
    private String name;
    private String description;
    private double price;
    private String imageurl;
    private Long restaurant;
    public DishRequest(String name, String description, double price, Long restaurant) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
    }

    
}
