package com.fooddelivery.backend.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fooddelivery.backend.Models.Basket;
import com.fooddelivery.backend.Models.BasketDish;
import com.fooddelivery.backend.Models.Dish;
import com.fooddelivery.backend.Models.Response.BasketDishResponse;
import com.fooddelivery.backend.Models.Response.BasketResponse;
import com.fooddelivery.backend.Models.Response.DishResponse;

@Component
public class BasketDishMapper {
    
    @Autowired
    DishMapper dishMapper;

    private DishResponse mapDishToResponse(Dish dish) {
         ModelMapper modelMapper = new ModelMapper();
        TypeMap<Dish, DishResponse> typeMap = modelMapper.createTypeMap(Dish.class, DishResponse.class);
        typeMap.addMappings(mapper -> {
            mapper.map(src -> src.getRestaurant().getId(), DishResponse::setRestaurant);
        });
       
        DishResponse res = modelMapper.map(dish, DishResponse.class);
        res.setRestaurant(dish.getRestaurant().getId());
        return res;
    }

    public BasketDishResponse mapBasketDishToResponse(BasketDish basketDish) {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<BasketDish, BasketDishResponse> typemap = modelMapper.createTypeMap(BasketDish.class, BasketDishResponse.class);
        typemap.addMapping(src -> src.getBasket().getId(), BasketDishResponse::setBasket);
        // typemap.addMapping(src -> new DishResponse(src.getDish().getId(), src.getDish().getName(), src.getDish().getImageurl(), src.getDish().getDescription(), src.getDish().getPrice(), src.getDish().getAvailability(), src.getDish().getRestaurant().getId(), src.getDish().getCreatedDate(), src.getDish().getUpdatedDate()), BasketDishResponse::setDish);
        BasketDishResponse res = modelMapper.map(basketDish, BasketDishResponse.class);
        res.setDishResponse(dishMapper.mapDishToResponse(basketDish.getDish()));
        // res.setBasket(basketDish.getBasket().getId());
        return res;
    }

     
}
