package com.fooddelivery.backend.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.backend.Mapper.OrderDishMapper;

import com.fooddelivery.backend.Models.OrderDish;
import com.fooddelivery.backend.Models.Response.DishResponse;
import com.fooddelivery.backend.Models.Response.OrderDishResponse;
import com.fooddelivery.backend.Service.OrderDishService;

@RestController
@RequestMapping("/api/orderdishes")
public class OrderDishController {
    @Autowired
    OrderDishMapper orderDishMapper;
    @Autowired
    OrderDishService orderDishService;

    // get order dish by  order dish Id
    @GetMapping("/orderdish/{id}")
    public ResponseEntity<OrderDishResponse> getById(@PathVariable Long id) {
        OrderDishResponse res = orderDishMapper.mapOrderDishToMap(orderDishService.getById(id));
        return new ResponseEntity<OrderDishResponse>(res, HttpStatus.OK);
    }

    // get order dishes by order Id
    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderDishResponse>> getByOrderId(@PathVariable Long id) {
        List<OrderDish> dishes = orderDishService.getListByOrder(id); 
        List<OrderDishResponse> res = dishes.stream().map(dish -> orderDishMapper.mapOrderDishToMap(dish)).collect(Collectors.toList());
        return new ResponseEntity<List<OrderDishResponse>>(res, HttpStatus.OK);
    }
}
