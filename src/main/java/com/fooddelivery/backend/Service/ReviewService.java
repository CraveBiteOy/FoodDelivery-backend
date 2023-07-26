package com.fooddelivery.backend.Service;

import com.fooddelivery.backend.Models.Review;

public interface ReviewService {
    Review getById(Long id);
    Review save(Long restaurantId, int rate);
    Review getByRestaurantAndAuthcustomer(Long restaurantId);
    Boolean checkIsRatedOrNot(Long restaurantId);
}
