package com.fooddelivery.backend.Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fooddelivery.backend.Models.Courier;
import com.fooddelivery.backend.Models.Customer;
import com.fooddelivery.backend.Models.Order;
import com.fooddelivery.backend.Models.Restaurant;
import  com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Models.Enums.OrderStatus;

@Repository
public interface OrderRepos extends JpaRepository<Order, Long>{
    List<Order> findByRestaurant(Restaurant restaurant);
    List<Order> findByRestaurantAndStatus(Restaurant restaurant, OrderStatus status);
    List<Order> findByCustomer(Customer customer);
    List<Order> findByCustomerAndStatus(Customer customer, OrderStatus status);
    List<Order> findByCourier(Courier courier);
    List<Order> findByCourierAndStatus(Courier courier, OrderStatus status);
}
