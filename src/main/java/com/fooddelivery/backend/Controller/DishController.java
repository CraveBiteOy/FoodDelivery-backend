package com.fooddelivery.backend.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.backend.Mapper.DishMapper;
import com.fooddelivery.backend.Models.Dish;
import com.fooddelivery.backend.Models.Request.DishRequest;
import com.fooddelivery.backend.Models.Response.DishResponse;
import com.fooddelivery.backend.Models.Response.OwnerResponse;
import com.fooddelivery.backend.Service.DishService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/dishes")
public class DishController {
    @Autowired
    DishMapper dishMapper;
    @Autowired
    DishService dishService;

    // get dish by by dish Id
    @GetMapping("/dish/{id}")
    public ResponseEntity<DishResponse> getById(@PathVariable Long id) {
        DishResponse res = dishMapper.mapDishToResponse(dishService.getById(id));
        return new ResponseEntity<DishResponse>(res, HttpStatus.OK);
    }
    // get dishes by by restaurant Id
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<DishResponse>> getByRestaurantId(@PathVariable Long id) {
        List<Dish> dishes = dishService.getDishesByRestaurant(id); 
        List<DishResponse> res = dishes.stream().map(dish -> dishMapper.mapDishToResponse(dish)).collect(Collectors.toList());
        return new ResponseEntity<List<DishResponse>>(res, HttpStatus.OK);
    }
    // add new dish
    @PostMapping("/dish")
    public ResponseEntity<DishResponse> save(@Valid @RequestBody DishRequest req) {
        DishResponse res = dishMapper.mapDishToResponse(dishService.save(req));
        return new ResponseEntity<DishResponse>(res, HttpStatus.OK);
    }
    // update dish by by dish Id
    @PutMapping("/dish/{id}")
    public ResponseEntity<DishResponse> update(@PathVariable Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String description, @RequestParam(required = false) Double price, @RequestParam(required = false) String imageurl) {
        DishResponse res = dishMapper.mapDishToResponse(dishService.update(id, name, description, price, imageurl));
        return new ResponseEntity<DishResponse>(res, HttpStatus.OK);
    }
    // update the available status of dish (true or false)
    @PutMapping("/dish/{id}/availability/{availability}")
    public ResponseEntity<DishResponse> modifyAvailability(@PathVariable Long id, @PathVariable String availability) {
        boolean isAvailability = availability.equals("true") ? true : false;
        DishResponse res = dishMapper.mapDishToResponse(dishService.modifyAvailable(id, isAvailability));
        return new ResponseEntity<DishResponse>(res, HttpStatus.OK);
    }
}
