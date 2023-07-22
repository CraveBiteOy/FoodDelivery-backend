package com.fooddelivery.backend.Service;

import java.util.List;

import com.fooddelivery.backend.Models.Restaurant;
import com.fooddelivery.backend.Models.Request.RestaurantRequest;
import com.fooddelivery.backend.Models.Response.RestaurantResponse;

public interface RestaurantService {
    Restaurant save(RestaurantRequest req);
    Restaurant update(Long id, String name,  String imageurl, Integer cookingTime);
    Restaurant getById(Long id);
    RestaurantResponse getByIdAndAuthenticatedCustomer(Long id);
    Restaurant getByName(String name);
    Restaurant updateReview(Long id, int rating);
    List<RestaurantResponse> getRestauranstsByDistanceOfCustomer();
    List<Restaurant> getAll();
    List<Restaurant> getByOwner(Long ownerId);
    Restaurant getFirstRestaurantForAuthOwner();
    Boolean checkRestaurantsOfAuthOwner();
}
