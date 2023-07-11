package com.fooddelivery.backend.Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fooddelivery.backend.Models.Order;
import com.fooddelivery.backend.Models.OrderDish;
import  com.fooddelivery.backend.Models.Users;

@Repository
public interface OrderDishRepos extends JpaRepository<OrderDish, Long> {
    List<OrderDish> findByOrder(Order order);
}
