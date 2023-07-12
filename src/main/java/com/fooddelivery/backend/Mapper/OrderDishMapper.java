package com.fooddelivery.backend.Mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fooddelivery.backend.Models.Order;
import com.fooddelivery.backend.Models.OrderDish;
import com.fooddelivery.backend.Models.Response.BasketDishResponse;
import com.fooddelivery.backend.Models.Response.OrderDishResponse;
import com.fooddelivery.backend.Models.Response.OrderDishResponse;

@Component
public class OrderDishMapper {
    @Autowired
    DishMapper dishMapper;


    public OrderDishResponse mapOrderDishToMap(OrderDish orderDish) {
        // ModelMapper modelMapper = new ModelMapper();
        // TypeMap<OrderDish, OrderDishResponse> typeMap = modelMapper.createTypeMap(OrderDish.class, OrderDishResponse.class);
        // typeMap.addMapping(src -> src.getOrder().getId(), OrderDishResponse::setOrder);
        // OrderDishResponse res = modelMapper.map(orderDish, OrderDishResponse.class);
        // res.setDishResponse(dishMapper.mapDishToResponse(orderDish.getDish()));
        OrderDishResponse res = new OrderDishResponse(orderDish.getId(), orderDish.getQuantity(), dishMapper.mapDishToResponse(orderDish.getDish()), orderDish.getOrder().getId());
        return res;
    }
}
