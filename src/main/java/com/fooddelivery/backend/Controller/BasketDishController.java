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
import com.fooddelivery.backend.Mapper.BasketDishMapper;
import com.fooddelivery.backend.Models.BasketDish;
import com.fooddelivery.backend.Models.Response.BasketDishResponse;
import com.fooddelivery.backend.Service.BasketDishService;



@RestController
@RequestMapping("/api/BasketDishes")
public class BasketDishController {
    @Autowired
    BasketDishService basketDishService;
    @Autowired
    BasketDishMapper basketDishMapper;

    // get basket dish by id
    @GetMapping("/id/{id}")
    public ResponseEntity<BasketDishResponse> getById(@PathVariable Long id) {
        BasketDishResponse res = basketDishMapper.mapBasketDishToResponse(basketDishService.getById(id));
        return new ResponseEntity<BasketDishResponse>(res, HttpStatus.OK);
    }
    // get basket dish by dish Id and basket Id
    @GetMapping("/dish/{dishId}/basket/{basketId}")
    public ResponseEntity<BasketDishResponse> getByDishAndBasket(@PathVariable Long dishId, @PathVariable Long basketId) {
        BasketDishResponse res = basketDishMapper.mapBasketDishToResponse(basketDishService.getByDishAndBasket(dishId, basketId));
        return new ResponseEntity<BasketDishResponse>(res, HttpStatus.OK);
    }

    // get list of basket dishes by basket Id
    @GetMapping("/basket/{basketId}")
    public ResponseEntity<List<BasketDishResponse>> getByBasket(@PathVariable Long basketId) {
        List<BasketDish> list = basketDishService.getByBasket(basketId);
        List<BasketDishResponse> res = list.stream().map(bd -> basketDishMapper.mapBasketDishToResponse(bd)).collect(Collectors.toList());
        return new ResponseEntity<List<BasketDishResponse>>(res, HttpStatus.OK);
    }
}
