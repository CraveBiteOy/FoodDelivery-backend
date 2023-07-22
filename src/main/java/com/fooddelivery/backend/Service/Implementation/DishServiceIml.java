package com.fooddelivery.backend.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.backend.Exception.BadResultException;
import com.fooddelivery.backend.Exception.EntityNotFoundException;
import com.fooddelivery.backend.Models.Dish;
import com.fooddelivery.backend.Models.Owner;
import com.fooddelivery.backend.Models.Restaurant;
import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Models.Request.DishRequest;
import com.fooddelivery.backend.Repository.DishRepos;
import com.fooddelivery.backend.Repository.RestaurantRepos;
import com.fooddelivery.backend.Service.DishService;
import com.fooddelivery.backend.Service.OwnerService;
import com.fooddelivery.backend.Service.RestaurantService;
import com.fooddelivery.backend.Service.UserService;

@Service
public class DishServiceIml implements DishService {
    @Autowired
    DishRepos dishRepos;
    @Autowired
    OwnerService ownerService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    UserService userService;
    @Autowired
    RestaurantRepos restaurantRepos;

    @Override
    public List<Dish> getDishesByRestaurant(long restaurantId) {
       Restaurant restaurant = restaurantService.getById(restaurantId);
       List<Dish> dishes = dishRepos.findByRestaurant(restaurant);
       return dishes;
    }

    @Override
    public Dish save(DishRequest req) {
        Restaurant restaurant = restaurantService.getById(req.getRestaurant());
        if(req.getPrice() < 0) {
            throw new BadResultException("the price must be higher than 0");
        }
        checkIsOwnerOfRestaurant(restaurant);
        Dish dish = new Dish(req.getName(), req.getDescription(), req.getPrice(), restaurant);
        if(req.getImageurl() != null) {
            dish.setImageurl(req.getImageurl());
        }
        dish = dishRepos.save(dish);
        restaurant.getDishes().add(dish);
        restaurantRepos.save(restaurant);
        return dish;

    }

    @Override
    public Dish modifyAvailable(long dishId, boolean availability) {
        Dish dish = getById(dishId);
        Restaurant restaurant = dish.getRestaurant();
        checkIsOwnerOfRestaurant(restaurant);
        dish.setAvailability(availability);
        return dishRepos.save(dish);
    }

    @Override
    public Dish update(long dishId, String name, String description, Double price, String imageurl, Boolean availability) {
        Dish dish = getById(dishId);
        Restaurant restaurant = dish.getRestaurant();
        checkIsOwnerOfRestaurant(restaurant);
        if(name != null  && name.length() > 0) {
            dish.setName(name);
        }
        if(description != null  && description.length() > 0) {
            dish.setDescription(description);
        }
        if(price != null) {
            dish.setPrice(price);
        }
        if(imageurl != null && imageurl.length() > 0) {
            dish.setImageurl(imageurl);
        }
        if(availability != null) {
            dish.setAvailability(availability);
        }
        return dishRepos.save(dish);
    }

    @Override
    public Dish getById(Long id) {
        Optional<Dish> entity = dishRepos.findById(id);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the dish not found");
        }
        return entity.get();
    }

    private void checkIsOwnerOfRestaurant(Restaurant restaurant) {
        Users authUser = userService.getAuthUser();
        Owner owner = restaurant.getOwner();
        if(authUser.getId() != owner.getUser().getId()) {
            throw new BadResultException("the authUser and restaurant owner are not the same, is not authorized");
        }
    }
    
}
