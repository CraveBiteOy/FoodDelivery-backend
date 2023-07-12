package com.fooddelivery.backend.Models.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest {
     private String name;
     private String address;
     private String imageurl;
     private String city;
     private String zipcode;
     private Double latitude;
     private Double longitude;
     private int cookingTime;

     public RestaurantRequest(String name, String address, String imageurl, String city, String zipcode, int cookingTime) {
          this.name = name;
          this.address = address;
          this.imageurl = imageurl;
          this.city = city;
          this.zipcode = zipcode;
          this.cookingTime = cookingTime;
     }
     
}
