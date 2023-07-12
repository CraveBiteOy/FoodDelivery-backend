package com.fooddelivery.backend.Mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fooddelivery.backend.Models.Customer;
import com.fooddelivery.backend.Models.Order;
import com.fooddelivery.backend.Models.Owner;
import com.fooddelivery.backend.Models.Response.CustomerResponse;
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
        
        OrderResponse res = new OrderResponse(order.getId(), customerMapper.mapCustomerToResponse(order.getCustomer()), restaurantMapper.mapRestaurantToResponse(order.getRestaurant()), order.getStatus(), order.getTotal(), order.getDeliveryFee(), order.getFinalPrice(), order.getQuantity(), order.getNote(), order.getToLongitude(), order.getToLatitude(), order.getFromLongitude(), order.getFromLatitude(), order.getCreatedDate(), order.getUpdatedDate());
        res.setTotalTime(order.getTotalTime());
        res.setD2Distance(order.getD2Distance());
        if(order.getCourier() != null) {
            res.setCourier(courierMapper.mapCourierToResponse(order.getCourier()));
        }

        System.out.println(res);
        return res;
    }
}
