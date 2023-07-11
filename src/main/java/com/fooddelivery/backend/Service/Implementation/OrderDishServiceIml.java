package com.fooddelivery.backend.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.backend.Exception.EntityNotFoundException;
import com.fooddelivery.backend.Models.Order;
import com.fooddelivery.backend.Models.OrderDish;
import com.fooddelivery.backend.Repository.OrderDishRepos;
import com.fooddelivery.backend.Repository.OrderRepos;
import com.fooddelivery.backend.Service.OrderDishService;

@Service 
public class OrderDishServiceIml implements OrderDishService{
    @Autowired
    OrderDishRepos orderDishRepos;
    @Autowired
    OrderRepos orderRepos;

    @Override
    public OrderDish getById(Long id) {
        Optional<OrderDish> entity = orderDishRepos.findById(id);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the orderDish not found");
        }
        return entity.get();
    }

    @Override
    public List<OrderDish> getListByOrder(Long orderID) {
       Order order = findOrderById(orderID);
       return orderDishRepos.findByOrder(order);
    }

    private Order findOrderById(Long orderId) {
        Optional<Order> entity = orderRepos.findById(orderId);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the order not found");
        }
        return entity.get();
    }
}
