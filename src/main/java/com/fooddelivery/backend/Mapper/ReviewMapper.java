package com.fooddelivery.backend.Mapper;

import org.springframework.stereotype.Component;

import com.fooddelivery.backend.Models.Review;
import com.fooddelivery.backend.Models.Response.ReviewResponse;

@Component
public class ReviewMapper {
    
    public ReviewResponse mapReviewToResponse(Review review) {
        ReviewResponse res = ReviewResponse.builder()
                                .id(review.getId())
                                .rate(review.getRate())
                                .customer(review.getCustomer().getId())
                                .restaurant(review.getRestaurant().getId())
                                .createdDate(review.getCreatedDate())
                                .updatedDate(review.getUpdatedDate())
                                .build();
        return res;
    }
}
