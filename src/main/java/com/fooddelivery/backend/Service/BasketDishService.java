package com.fooddelivery.backend.Service;

import java.util.List;

import com.fooddelivery.backend.Models.Basket;
import com.fooddelivery.backend.Models.BasketDish;

public interface BasketDishService {
    BasketDish turnDishIntoBasketDish(Long dishId, Long basketId, int quantity);
    void removeBasketDish(Long basketDishId);
    BasketDish update(Long basketDishID, int quantity);
    BasketDish getById(Long id);
    BasketDish getByDishAndBasket(Long dishId, Long basketId);
    List<BasketDish> getByBasket(Long basketId);
}
