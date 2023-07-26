package com.fooddelivery.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.backend.Models.Customer;
import com.fooddelivery.backend.Models.Restaurant;
import com.fooddelivery.backend.Models.Review;

@Repository
public interface ReviewRepos extends JpaRepository<Review, Long> {
    Optional<Review> findByRestaurantAndCustomer(Restaurant restaurant, Customer customer);
}
