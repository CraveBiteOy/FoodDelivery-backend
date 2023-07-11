package com.fooddelivery.backend.Mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fooddelivery.backend.Models.Order;
import com.fooddelivery.backend.Models.Owner;
import com.fooddelivery.backend.Models.Response.OrderResponse;
import com.fooddelivery.backend.Models.Response.OwnerResponse;

@Component
public class OrderMapper {

    @Autowired
    RestaurantMapper restaurantMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    CourierMapper courierMapper;

    public OrderResponse mapOrderToResponse(Order order) {
        ModelMapper modelMapper = new ModelMapper();
        // TypeMap<Order, OrderResponse> typeMap = modelMapper.createTypeMap(Order.class, OrderResponse.class);
        // typeMap.addMappings(mapper -> {
        //   mapper.map(src -> userMapper.mapUserToResponse(src.getUser()), OwnerResponse::setUser);
        // });
        OrderResponse res = modelMapper.map(order, OrderResponse.class);
        res.setCustomerRes(customerMapper.mapCustomerToResponse(order.getCustomer()));
        res.setRestaurantRes(restaurantMapper.mapRestaurantToResponse(order.getRestaurant()));
        res.setCourierRes(courierMapper.mapCourierToResponse(order.getCourier()));
        System.out.println(res);
        return res;
    }
}
