package com.fooddelivery.backend.Mapper;

import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fooddelivery.backend.Models.Dish;
import com.fooddelivery.backend.Models.Restaurant;
import com.fooddelivery.backend.Models.Response.DishResponse;
import com.fooddelivery.backend.Models.Response.RestaurantResponse;
import com.fooddelivery.backend.Repository.DishRepos;

@Component
public class RestaurantMapper {
    @Autowired
    DishRepos dishRepos;
    @Autowired
    DishMapper dishMapper;

    public RestaurantResponse mapRestaurantToResponse(Restaurant restaurant) {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Restaurant, RestaurantResponse> typeMap = modelMapper.createTypeMap(Restaurant.class, RestaurantResponse.class);
        typeMap.addMappings(mapper -> {
            mapper.map(src -> src.getOwner().getId(), RestaurantResponse::setOwner);
        });
        RestaurantResponse res = modelMapper.map(restaurant, RestaurantResponse.class);
        // res.setOwner(restaurant.getOwner().getId());
        // List<Dish> dishes = dishRepos.findByRestaurant(restaurant);
        // if(dishes.size() > 0) {
        //     List<DishResponse> dishesRes = dishes.stream().filter(ruoka -> ruoka.getAvailability() == true).map(ruoka -> dishMapper.mapDishToResponse(ruoka)).collect(Collectors.toList());
        //     res.getDishes().addAll(dishesRes);
        // }
        return res;
    }
}
