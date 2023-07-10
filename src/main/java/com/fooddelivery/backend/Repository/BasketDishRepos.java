package com.fooddelivery.backend.Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fooddelivery.backend.Models.Basket;
import com.fooddelivery.backend.Models.BasketDish;
import com.fooddelivery.backend.Models.Dish;
import  com.fooddelivery.backend.Models.Users;

@Repository
public interface BasketDishRepos extends JpaRepository<BasketDish, Long> {
    Optional<BasketDish> findByDishAndBasket(Dish dish, Basket basket);
    List<BasketDish> findByBasket(Basket basket);
}
