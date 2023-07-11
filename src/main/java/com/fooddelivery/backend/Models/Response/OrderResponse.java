package com.fooddelivery.backend.Models.Response;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fooddelivery.backend.Models.Enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private CustomerResponse customerRes;
    private RestaurantResponse restaurantRes;
    private CourierResponse courierRes;
    private OrderStatus status;
    private double total;
    private double deliveryFee;
    private double finalPrice;
    private int quantity;
    private String note;
    private Double toLongitude;
    private Double toLatitude;
    private Double fromLongitude;
    private Double fromLatitude;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedDate;

    // public OrderResponse(Long id, CustomerResponse customer, RestaurantResponse restaurant, OrderStatus status, double total, int quantity, String note, Double toLongitude, Double toLatitude, Double fromLongitude, Double fromLatitude, LocalDateTime createdDate, LocalDateTime updatedDate) {
    //     this.id = id;
    //     this.customerRes = customer;
    //     this.restaurantRes = restaurant;
    //     this.status = status;
    //     this.total = total;
    //     this.quantity = quantity;
    //     this.note = note;
    //     this.toLongitude = toLongitude;
    //     this.toLatitude = toLatitude;
    //     this.fromLongitude = fromLongitude;
    //     this.fromLatitude = fromLatitude;
    //     this.createdDate = createdDate;
    //     this.updatedDate = updatedDate;
    // }

    
}
