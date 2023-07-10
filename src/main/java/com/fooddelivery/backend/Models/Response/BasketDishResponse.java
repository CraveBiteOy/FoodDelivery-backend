package com.fooddelivery.backend.Models.Response;
import com.fooddelivery.backend.Models.Dish;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketDishResponse {
    private Long id;
    private int quantity;
    private DishResponse dishResponse;
    private Long basket;
}
