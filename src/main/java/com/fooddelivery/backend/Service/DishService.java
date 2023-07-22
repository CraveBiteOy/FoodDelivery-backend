package com.fooddelivery.backend.Service;

import java.util.List;

import com.fooddelivery.backend.Models.Dish;
import com.fooddelivery.backend.Models.Request.DishRequest;

public interface DishService {
    Dish save(DishRequest req);
    Dish update(long dishId, String name, String description, Double price, String imageurl, Boolean availability);
    Dish modifyAvailable(long dishId, boolean availability);
    List<Dish> getDishesByRestaurant(long restaurantId); 
    Dish getById(Long id);
}
