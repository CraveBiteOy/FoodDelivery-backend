package com.fooddelivery.backend.Service;

import com.fooddelivery.backend.Models.Basket;

public interface BasketService {
    Basket getByAuthUserAndRestaurant(Long restaurantId);
    Basket getByCustomerAndRestaurant(Long customerId, Long restaurantId);
    Basket getById(Long id);
    Basket addDishToBasket(Long dishId, int quantity, Long basketId);
    Basket removeBasketDishFromBasket(Long basketDishId);
    Basket removeAllDishesFromBasket(Long id);
}
