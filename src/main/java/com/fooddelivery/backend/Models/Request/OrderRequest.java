package com.fooddelivery.backend.Models.Request;

import com.fooddelivery.backend.Models.Customer;
import com.fooddelivery.backend.Models.Restaurant;
import com.fooddelivery.backend.Models.Response.CustomerResponse;
import com.fooddelivery.backend.Models.Response.RestaurantResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long basketID;
    private String address;
    private String zipcode;
    private String city;
    private String note;
    private Double toLatitude;
    private Double toLongitude;


    public OrderRequest(Long basketID, String address, String zipcode, String city) {
        this.basketID = basketID;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
    }


    public OrderRequest(Long basketID, String address, String zipcode, String city, String note) {
        this.basketID = basketID;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.note = note;
    }


    public OrderRequest(Long basketID, String note, Double toLatitude, Double toLongitude) {
        this.basketID = basketID;
        this.note = note;
        this.toLatitude = toLatitude;
        this.toLongitude = toLongitude;
    }


    public OrderRequest(Long basketID, Double toLatitude, Double toLongitude) {
        this.basketID = basketID;
        this.toLatitude = toLatitude;
        this.toLongitude = toLongitude;
    }

    

    
    
    


    
}
