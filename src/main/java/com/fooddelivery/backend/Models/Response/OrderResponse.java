package com.fooddelivery.backend.Models.Response;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fooddelivery.backend.Models.Enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private CustomerResponse customer;
    private RestaurantResponse restaurant;
    private CourierResponse courier;
    private OrderStatus status;
    private double total;
    private double deliveryFee;
    private double finalPrice;
    private int quantity;
    private String note;
    private double d2Distance;
    private int totalTime;
    // pickedupTime and dropOffTime
    private int pickedupTime;
    private int dropOffTime;
    // moving the matching courier to the owener-accepting stage
    private Double toLongitude;
    private Double toLatitude;
    private Double fromLongitude;
    private Double fromLatitude;
    private String address;
    private String zipcode;
    private String city;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedDate;
    public OrderResponse(Long id, CustomerResponse customer, RestaurantResponse restaurant, OrderStatus status, double total, double deliveryFee, double finalPrice, int quantity, String note, Double toLongitude, Double toLatitude, Double fromLongitude, Double fromLatitude, LocalDateTime createdDate, LocalDateTime updatedDate, String address, String zipcode, String city) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.status = status;
        this.total = total;
        this.deliveryFee = deliveryFee;
        this.finalPrice = finalPrice;
        this.quantity = quantity;
        this.note = note;
        this.toLongitude = toLongitude;
        this.toLatitude = toLatitude;
        this.fromLongitude = fromLongitude;
        this.fromLatitude = fromLatitude;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
    }

    

    
}
