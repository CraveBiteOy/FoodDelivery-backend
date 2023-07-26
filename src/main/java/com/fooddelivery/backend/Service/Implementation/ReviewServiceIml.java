package com.fooddelivery.backend.Service.Implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.backend.Exception.BadResultException;
import com.fooddelivery.backend.Exception.EntityNotFoundException;
import com.fooddelivery.backend.Models.Customer;
import com.fooddelivery.backend.Models.Restaurant;
import com.fooddelivery.backend.Models.Review;
import com.fooddelivery.backend.Repository.RestaurantRepos;
import com.fooddelivery.backend.Repository.ReviewRepos;
import com.fooddelivery.backend.Service.CustomerService;
import com.fooddelivery.backend.Service.RestaurantService;
import com.fooddelivery.backend.Service.ReviewService;
import com.fooddelivery.backend.Service.UserService;

@Service
public class ReviewServiceIml implements ReviewService {

    @Autowired
    UserService userService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ReviewRepos reviewRepos;
    @Autowired
    RestaurantRepos restaurantRepos;

    @Override
    public Boolean checkIsRatedOrNot(Long restaurantId) {
        Customer authCustomer = customerService.getByAuthenticatedUser();
        Restaurant restaurant = restaurantService.getById(restaurantId);
        Optional<Review> entity = reviewRepos.findByRestaurantAndCustomer(restaurant, authCustomer);
        if (!entity.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Review getById(Long id) {
         Optional<Review> entity = reviewRepos.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException("the review not found");
        }
    }

    @Override
    public Review getByRestaurantAndAuthcustomer(Long restaurantId) {
        Customer authCustomer = customerService.getByAuthenticatedUser();
        Restaurant restaurant = restaurantService.getById(restaurantId);
        Optional<Review> entity = reviewRepos.findByRestaurantAndCustomer(restaurant, authCustomer);
        if (!entity.isPresent()) {
            throw new EntityNotFoundException("the review not found");
        } else {
            return entity.get();
        }
    }

    @Override
    public Review save(Long restaurantId, int rate) {
        Boolean isReviewed = checkIsRatedOrNot(restaurantId);
        if(isReviewed) {
            throw new BadResultException("only add one review for one restaurant");
        } else {
            Customer authCustomer = customerService.getByAuthenticatedUser();
            Restaurant restaurant = restaurantService.getById(restaurantId);
            Review review = new Review(rate, authCustomer, restaurant);
            restaurant.setRating((restaurant.getRating() + Double.valueOf((double)rate)) / 2);
            restaurantRepos.save(restaurant);
            return reviewRepos.save(review);
        }
    }
    

    
}
