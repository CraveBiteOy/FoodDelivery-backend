package com.fooddelivery.backend.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.fooddelivery.backend.Models.Customer;
import com.fooddelivery.backend.Models.Dish;
import com.fooddelivery.backend.Models.Restaurant;
import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Models.Response.CustomerResponse;
import com.fooddelivery.backend.Models.Response.DishResponse;
import com.fooddelivery.backend.Models.Response.RestaurantResponse;
import com.fooddelivery.backend.Models.Response.UserResponse;

@Component
public class DishMapper {
    

    public DishResponse mapDishToResponse(Dish dish) {
         ModelMapper modelMapper = new ModelMapper();
        TypeMap<Dish, DishResponse> typeMap = modelMapper.createTypeMap(Dish.class, DishResponse.class);
        typeMap.addMappings(mapper -> {
            mapper.map(src -> src.getRestaurant().getId(), DishResponse::setRestaurant);
        });
       
        DishResponse res = modelMapper.map(dish, DishResponse.class);
        res.setRestaurant(dish.getRestaurant().getId());
        return res;
    }
}
