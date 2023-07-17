package com.fooddelivery.backend.Controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fooddelivery.backend.Mapper.BasketDishMapper;
import com.fooddelivery.backend.Mapper.BasketMapper;
import com.fooddelivery.backend.Models.BasketDish;
import com.fooddelivery.backend.Models.Response.BasketDishResponse;
import com.fooddelivery.backend.Models.Response.BasketResponse;
import com.fooddelivery.backend.Service.BasketDishService;
import com.fooddelivery.backend.Service.BasketService;



@RestController
@RequestMapping("/api/baskets")
public class BasketController {
    @Autowired
    BasketMapper basketMapper;
    @Autowired
    BasketService basketService;

    // get basket by id
    @GetMapping("/basket/id/{id}")
    public ResponseEntity<BasketResponse> getById(@PathVariable Long id) {
        BasketResponse res = basketMapper.mapBasketToResponse(basketService.getById(id));
        return new ResponseEntity<BasketResponse>(res, HttpStatus.OK);
    }

    // get basket by authenticated user and restaurant Id
    @GetMapping("/authenticatedUser/restaurant/{restaurantId}")
    public ResponseEntity<BasketResponse> getByAuthUserAndRestaurant(@PathVariable Long restaurantId) {
        BasketResponse res = basketMapper.mapBasketToResponse(basketService.getByAuthUserAndRestaurant(restaurantId));
        return new ResponseEntity<BasketResponse>(res, HttpStatus.OK);
    }
    // get basket by customer and restaurant Id
    @GetMapping("/customer/{customerId}/restaurant/{restaurantId}")
    public ResponseEntity<BasketResponse> getByCustomerAndRestaurant(@PathVariable Long customerId, @PathVariable Long restaurantId) {
        BasketResponse res = basketMapper.mapBasketToResponse(basketService.getByCustomerAndRestaurant(customerId, restaurantId));
        return new ResponseEntity<BasketResponse>(res, HttpStatus.OK);
    }
    //  // add dish to basket
    // @PutMapping("/basket/{basketId}/dish/{dishId}/quantity/{quantity}")
    // public ResponseEntity<BasketResponse> addDish(@PathVariable Long basketId, @PathVariable Long dishId, @PathVariable int quantity) {
    //     BasketResponse res = basketMapper.mapBasketToResponse(basketService.addDishToBasket(dishId, quantity, basketId));
    //     return new ResponseEntity<BasketResponse>(res, HttpStatus.OK);
    // }
    //  // remove dish from basket
    // @PutMapping("/remove/basketDish/{basketDishId}/")
    // public ResponseEntity<BasketResponse> removeDish(@PathVariable Long basketDishId) {
    //     BasketResponse res = basketMapper.mapBasketToResponse(basketService.removeBasketDishFromBasket(basketDishId));
    //     return new ResponseEntity<BasketResponse>(res, HttpStatus.OK);
    // }
     // remove all dishes from basket
    @PutMapping("/remove/all/basket/{id}")
    public ResponseEntity<BasketResponse> removeAll(@PathVariable Long id) {
        BasketResponse res = basketMapper.mapBasketToResponse(basketService.removeAllDishesFromBasket(id));
        return new ResponseEntity<BasketResponse>(res, HttpStatus.OK);
    }
}
