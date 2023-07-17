package com.fooddelivery.backend.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.backend.Exception.EntityNotFoundException;
import com.fooddelivery.backend.Models.Basket;
import com.fooddelivery.backend.Models.BasketDish;
import com.fooddelivery.backend.Models.Customer;
import com.fooddelivery.backend.Models.Dish;
import com.fooddelivery.backend.Models.Restaurant;
import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Repository.BasketDishRepos;
import com.fooddelivery.backend.Repository.BasketRepos;
import com.fooddelivery.backend.Service.BasketDishService;
import com.fooddelivery.backend.Service.BasketService;
import com.fooddelivery.backend.Service.CustomerService;
import com.fooddelivery.backend.Service.DishService;
import com.fooddelivery.backend.Service.RestaurantService;
import com.fooddelivery.backend.Service.UserService;

@Service
public class BasketServiceIml implements BasketService{
    @Autowired
    CustomerService customerService;
    @Autowired
    UserService userService;
    @Autowired
    BasketRepos basketRepos;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    DishService dishService;
    @Autowired
    BasketDishService basketDishService;
    @Autowired
    BasketDishRepos basketDishRepos;

    @Override
    public Basket getByAuthUserAndRestaurant(Long restaurantId) {
        Users authUser = userService.getAuthUser();
        Customer customer = customerService.getByAuthenticatedUser();
        Restaurant restaurant = restaurantService.getById(restaurantId);
        Optional<Basket> entity = basketRepos.findByCustomerAndRestaurant(customer, restaurant);
        if(!entity.isPresent()) {
            Basket basket = new Basket(customer, restaurant);
            return basketRepos.save(basket);
        } else {
            return entity.get();
        }
    }

    @Override
    public Basket getByCustomerAndRestaurant(Long customerId, Long restaurantId) {
        Customer customer = customerService.getById(customerId);
        Restaurant restaurant = restaurantService.getById(restaurantId);
        Optional<Basket> entity = basketRepos.findByCustomerAndRestaurant(customer, restaurant);
        if(!entity.isPresent()) {
            Basket basket = new Basket(customer, restaurant);
            return basketRepos.save(basket);
        } else {
            return entity.get();
        }
    }

    @Override
    public Basket getById(Long id) {
        Optional<Basket> entity = basketRepos.findById(id);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the basket not found");
        } else {
            return entity.get();
        }
    }

    @Override
    public Basket addDishToBasket(Long dishId, int quantity, Long basketId) {
        BasketDish basketDish = basketDishService.turnDishIntoBasketDish(dishId, basketId, quantity);
        return getById(basketId);
    }

  
    @Override
    public Basket removeBasketDishFromBasket(Long basketDishId) {
        BasketDish basketDish = basketDishService.getById(basketDishId);
        Basket basket = basketDish.getBasket();
        basketDishService.removeBasketDish(basketDishId);
        return getById(basket.getId());
    }


    @Override
    public Basket removeAllDishesFromBasket(Long id) {
        Basket basket = getById(id);
        List<BasketDish> basketDishes = basketDishService.getByBasket(id);
        basketDishes.forEach(dish -> {
            basketDishRepos.delete(dish);
        });
        basket.setQuantity(0);
        basket.setTotal(0.00);
        basket = basketRepos.save(basket);
        return basket;
    }
}
