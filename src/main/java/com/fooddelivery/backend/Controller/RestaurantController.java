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
import com.fooddelivery.backend.Mapper.CustomerMapper;
import com.fooddelivery.backend.Mapper.RestaurantMapper;
import com.fooddelivery.backend.Models.Restaurant;
import com.fooddelivery.backend.Models.Request.RestaurantRequest;
import com.fooddelivery.backend.Models.Response.CustomerResponse;
import com.fooddelivery.backend.Models.Response.RestaurantResponse;
import com.fooddelivery.backend.Service.CustomerService;
import com.fooddelivery.backend.Service.RestaurantService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    RestaurantMapper restaurantMapper;

    // get restaurant by restaurant id
    @GetMapping("/restaurant/id/{id}")
    public ResponseEntity<RestaurantResponse> getById(@PathVariable Long id) {
        RestaurantResponse res = restaurantMapper.mapRestaurantToResponse(restaurantService.getById(id));
        return new ResponseEntity<RestaurantResponse>(res, HttpStatus.OK);
    }

    // get restaurant by restaurant id and authenticatedCustomer
    @GetMapping("/restaurant/authenticatedCustomer/id/{id}")
    public ResponseEntity<RestaurantResponse> getByIdAndCustomer(@PathVariable Long id) {
        RestaurantResponse res = restaurantService.getByIdAndAuthenticatedCustomer(id);
        return new ResponseEntity<RestaurantResponse>(res, HttpStatus.OK);
    }

    // update rating
    @GetMapping("/restaurant/id/{id}/rating/{rating}")
    public ResponseEntity<RestaurantResponse> updaterating(@PathVariable Long id, @PathVariable int rating) {
        RestaurantResponse res = restaurantMapper.mapRestaurantToResponse(restaurantService.updateReview(id, rating));
        return new ResponseEntity<RestaurantResponse>(res, HttpStatus.OK);
    }

    // get restaurant by restaurant name
    @GetMapping("/restaurant/name/{name}")
    public ResponseEntity<RestaurantResponse> getByName(@PathVariable String name) {
        RestaurantResponse res = restaurantMapper.mapRestaurantToResponse(restaurantService.getByName(name));
        return new ResponseEntity<RestaurantResponse>(res, HttpStatus.OK);
    }

    // get all restaurants
    @GetMapping("/all")
    public ResponseEntity<List<RestaurantResponse>> getAll() {
        List<Restaurant> list = restaurantService.getAll();
        List<RestaurantResponse> res = list.stream().map(restaurant -> restaurantMapper.mapRestaurantToResponse(restaurant)).collect(Collectors.toList());
        return new ResponseEntity<List<RestaurantResponse>>(res, HttpStatus.OK);
    }
    // get all restaurants for the specific owner
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<RestaurantResponse>> getAllOfOwner(@PathVariable Long ownerId) {
        List<Restaurant> list = restaurantService.getByOwner(ownerId);
        List<RestaurantResponse> res = list.stream().map(restaurant -> restaurantMapper.mapRestaurantToResponse(restaurant)).collect(Collectors.toList());
        return new ResponseEntity<List<RestaurantResponse>>(res, HttpStatus.OK);
    }

     //get all recommended restaurants for the authenticated customers based on his current location within 20km
    @GetMapping("/recommendation")
    public ResponseEntity<List<RestaurantResponse>> getRecommendation() {
        // List<Restaurant> list = restaurantService.getRestauranstsByDistanceOfCustomer();
        // List<RestaurantResponse> res = list.stream().map(restaurant -> restaurantMapper.mapRestaurantToResponse(restaurant)).collect(Collectors.toList());

        List<RestaurantResponse> res = restaurantService.getRestauranstsByDistanceOfCustomer();
        return new ResponseEntity<List<RestaurantResponse>>(res, HttpStatus.OK);
      
    }

    // create new restaurant
    @PostMapping("/restaurant")
    public ResponseEntity<RestaurantResponse> save(@Valid @RequestBody RestaurantRequest req) {
        RestaurantResponse res = restaurantMapper.mapRestaurantToResponse(restaurantService.save(req));
        return new ResponseEntity<RestaurantResponse>(res, HttpStatus.CREATED);
    }

     // update restaurant by restaurant id for new name or new image url
    @PutMapping("/restaurant/id/{id}")
    public ResponseEntity<RestaurantResponse> update(@PathVariable Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String imageurl) {
        RestaurantResponse res = restaurantMapper.mapRestaurantToResponse(restaurantService.update(id, name != null ? name : null, imageurl != null ? imageurl : null));
        return new ResponseEntity<RestaurantResponse>(res, HttpStatus.OK);
    }

}
