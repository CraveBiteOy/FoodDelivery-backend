package com.fooddelivery.backend.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import com.fooddelivery.backend.Models.Basket;
import com.fooddelivery.backend.Models.Dish;
import com.fooddelivery.backend.Models.Response.BasketResponse;
import com.fooddelivery.backend.Models.Response.DishResponse;

@Component
public class BasketMapper {
   
    
    public BasketResponse mapBasketToResponse(Basket basket) {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Basket, BasketResponse> typemapper = modelMapper.createTypeMap(Basket.class, BasketResponse.class);
        typemapper.addMapping(src -> src.getCustomer().getId(), BasketResponse::setCustomer);
        typemapper.addMapping(src -> src.getRestaurant().getId(), BasketResponse::setRestaurant);
        BasketResponse res = modelMapper.map(basket, BasketResponse.class);
        // res.setCustomer(basket.getCustomer().getId());
        // res.setRestaurant(basket.getRestaurant().getId());
        return res;
    }
}
