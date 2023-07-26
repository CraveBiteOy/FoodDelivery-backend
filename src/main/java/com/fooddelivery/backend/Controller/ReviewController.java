package com.fooddelivery.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.backend.Mapper.ReviewMapper;
import com.fooddelivery.backend.Models.Response.BasketResponse;
import com.fooddelivery.backend.Models.Response.ReviewResponse;
import com.fooddelivery.backend.Service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewMapper reviewMapper;

     // get Review by id
    @GetMapping("/review/id/{id}")
    public ResponseEntity<ReviewResponse> getById(@PathVariable Long id) {
        ReviewResponse res = reviewMapper.mapReviewToResponse(reviewService.getById(id));
        return new ResponseEntity<ReviewResponse>(res, HttpStatus.OK);
    }

     // get Review by Restaurant And Authenticated cusstomer
    @GetMapping("/review/restaurant/{restaurantID}")
    public ResponseEntity<ReviewResponse> getByRestaurantAndAuthCustomser(@PathVariable Long restaurantID) {
        ReviewResponse res = reviewMapper.mapReviewToResponse(reviewService.getByRestaurantAndAuthcustomer(restaurantID));
        return new ResponseEntity<ReviewResponse>(res, HttpStatus.OK);
    }
     // create new review from params with restaurantID and rate
    @PostMapping("/review/restaurant/{restaurantID}/rate/{rate}")
    public ResponseEntity<ReviewResponse> save(@PathVariable Long restaurantID, @PathVariable int rate) {
        ReviewResponse res = reviewMapper.mapReviewToResponse(reviewService.save(restaurantID, rate));
        return new ResponseEntity<ReviewResponse>(res, HttpStatus.CREATED);
    }

    // check existence of the Review by Restaurant And Authenticated cusstomer
    @GetMapping("/checkReview/restaurant/{restaurantID}")
    public ResponseEntity<Boolean> checkReviewByRestaurantAndAuthCustomer(@PathVariable Long restaurantID) {
        Boolean res = reviewService.checkIsRatedOrNot(restaurantID);
        return new ResponseEntity<Boolean>(res, HttpStatus.OK);
    }
}
