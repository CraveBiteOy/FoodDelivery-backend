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


    public BasketDishResponse mapBasketDishToResponse(BasketDish basketDish) {
        // ModelMapper modelMapper = new ModelMapper();
        // TypeMap<BasketDish, BasketDishResponse> typemap = modelMapper.createTypeMap(BasketDish.class, BasketDishResponse.class);

        // typemap.addMapping(src -> src.getBasket().getId(), BasketDishResponse::setBasket);
        // // typemap.addMapping(src -> new DishResponse(src.getDish().getId(), src.getDish().getName(), src.getDish().getImageurl(), src.getDish().getDescription(), src.getDish().getPrice(), src.getDish().getAvailability(), src.getDish().getRestaurant().getId(), src.getDish().getCreatedDate(), src.getDish().getUpdatedDate()), BasketDishResponse::setDish);


        // BasketDishResponse res = modelMapper.map(basketDish, BasketDishResponse.class);
        // res.setDishResponse(dishMapper.mapDishToResponse(basketDish.getDish()));
        // res.setBasket(basketDish.getBasket().getId());
        BasketDishResponse res = new BasketDishResponse(basketDish.getId(), basketDish.getQuantity(), dishMapper.mapDishToResponse(basketDish.getDish()), basketDish.getBasket().getId());
        return res;
    }

     
}
