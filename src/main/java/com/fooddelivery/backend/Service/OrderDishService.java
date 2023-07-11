package com.fooddelivery.backend.Service;

import java.util.List;

import com.fooddelivery.backend.Models.OrderDish;

public interface OrderDishService {
    List<OrderDish> getListByOrder(Long orderID);
    OrderDish getById(Long id);
}
