package com.fooddelivery.backend.Service.Implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.backend.Exception.BadResultException;
import com.fooddelivery.backend.Exception.EntityNotFoundException;
import com.fooddelivery.backend.Mapper.RestaurantMapper;
import com.fooddelivery.backend.Models.Customer;
import com.fooddelivery.backend.Models.Owner;
import com.fooddelivery.backend.Models.Restaurant;
import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Models.Request.RestaurantRequest;
import com.fooddelivery.backend.Models.Response.RestaurantResponse;
import com.fooddelivery.backend.Repository.OwnerRepos;
import com.fooddelivery.backend.Repository.RestaurantRepos;
import com.fooddelivery.backend.Repository.UserRepos;
import com.fooddelivery.backend.Service.CustomerService;
import com.fooddelivery.backend.Service.OwnerService;
import com.fooddelivery.backend.Service.RestaurantService;
import com.fooddelivery.backend.Service.UserService;
import com.fooddelivery.backend.Utils.DistanceCoding;
import com.fooddelivery.backend.Utils.GeoCoding;

@Service
public class RestaurantServiceIml implements RestaurantService {

    private final double fixedTimeForOneKilometer = 5;

    @Autowired
    UserService userService;
    @Autowired
    UserRepos userRepos;
    @Autowired
    OwnerRepos ownerRepos;
    @Autowired
    OwnerService ownerService;
    @Autowired
    RestaurantRepos restaurantRepos;
    @Autowired
    GeoCoding geoCoding;
    @Autowired
    DistanceCoding distanceCoding;
    @Autowired
    CustomerService customerService;
    @Autowired
    RestaurantMapper restaurantMapper;

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepos.findAll();
    }

    @Override
    public List<Restaurant> getByOwner(Long ownerId) {
        Owner owner = ownerService.getOwnerById(ownerId);
        return restaurantRepos.findByOwner(owner);
    }

     

    @Override
    public Restaurant getFirstRestaurantForAuthOwner() {
        Owner owner = ownerService.getOwnerByAuthenticatedUser();
        List<Restaurant> restaurants = restaurantRepos.findByOwner(owner);
        if(restaurants.size() > 0) {
            return restaurants.get(0);
        } else {
            throw new EntityNotFoundException("the owner does not have any restaurant");
        }
    }

    

    @Override
    public Boolean checkRestaurantsOfAuthOwner() {
        Owner owner = ownerService.getOwnerByAuthenticatedUser();
        List<Restaurant> restaurants = restaurantRepos.findByOwner(owner);
        if(restaurants.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Restaurant getById(Long id) {
        Optional<Restaurant> entity = restaurantRepos.findById(id);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the restaurant not found");
        }
        Restaurant restaurant = entity.get();
        return restaurant;
    }

    @Override
    public RestaurantResponse getByIdAndAuthenticatedCustomer(Long id) {
       Optional<Restaurant> entity = restaurantRepos.findById(id);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the restaurant not found");
        }
        Restaurant restaurant = entity.get();
        System.out.println(restaurant);
        RestaurantResponse res = restaurantMapper.mapRestaurantToResponse(restaurant);
        Customer customer = customerService.getByAuthenticatedUser();
        double distance = calculateDistance(customer, restaurant);
        res.setDistance(distance);
        int estimatedTime = restaurant.getCookingTime() + (int) Math.round(distance * fixedTimeForOneKilometer);
        res.setEstimatedTime(estimatedTime);
        System.out.println("distance " + distance + ", estimatedTime: " + estimatedTime);
        return res;
    }

    @Override
    public Restaurant updateReview(Long id, int rating) {
        Restaurant restaurant = getById(id);
        if(restaurant.getRating() == null) {
            restaurant.setRating(Double.valueOf(rating));
        } else {
            restaurant.setRating((Double.valueOf(rating) + restaurant.getRating()) / 2);
        }
        return restaurantRepos.save(restaurant);
    }

    @Override
    public Restaurant getByName(String name) {
        Optional<Restaurant> entity = restaurantRepos.findByName(name);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the restaurant not found");
        }
        Restaurant restaurant = entity.get();
        return restaurant;
    }


    // recommends restaurants for the authenticated customers based on his current location within 20km
    @Override
    public List<RestaurantResponse> getRestauranstsByDistanceOfCustomer() {
        Customer customer = customerService.getByAuthenticatedUser();
        List<Restaurant> list = restaurantRepos.findAll();
        List<RestaurantResponse> recommendRestaurants = list.stream().filter(restaurant -> {
            double distance = calculateDistance(customer, restaurant);
            return distance <= 20;
        }).map(restaurant -> {
            double distance = calculateDistance(customer, restaurant);
            RestaurantResponse res = restaurantMapper.mapRestaurantToResponse(restaurant);
            res.setDistance(distance);
            res.setEstimatedTime(restaurant.getCookingTime() + (int) Math.round(distance * fixedTimeForOneKilometer));
            return res;
        }).collect(Collectors.toList());
        return recommendRestaurants;
    }

    @Override
    public Restaurant save(RestaurantRequest req) {
        Users authUser = userService.getAuthUser();
        Owner owner = ownerService.getOwnerByAuthenticatedUser();
        Restaurant restaurant = new Restaurant(req.getName(), req.getAddress(), req.getZipcode(), req.getCity(), req.getImageurl(), owner);
        restaurant.setCookingTime(req.getCookingTime());
        req = geoCoding.geoLocationEncode(req);
        restaurant.setLatitude(req.getLatitude());
        restaurant.setLongitude(req.getLongitude());

        restaurant = restaurantRepos.save(restaurant);
        owner.getRestaurants().add(restaurant);
        ownerRepos.save(owner);
        return restaurant;
    }

    @Override
    public Restaurant update(Long id,String name,  String imageurl) {
        Restaurant restaurant = getById(id);
        if(name != null && name.length() > 0 && !name.equals(restaurant.getName())) {
            restaurant.setName(name);
        }
        if(imageurl != null && imageurl.length() > 0 && !imageurl.equals(restaurant.getImageurl())) {
            restaurant.setImageurl(imageurl);
        }
        return restaurantRepos.save(restaurant);
    }
    
    private double calculateDistance(Customer customer, Restaurant restaurant) {
        double distance = distanceCoding.distanceCalculator(customer.getUser().getLatitude(), restaurant.getLatitude(), customer.getUser().getLongitude(), restaurant.getLongitude());
        return distance;
    }
}
