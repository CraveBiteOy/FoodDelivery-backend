package com.fooddelivery.backend.Models.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDishResponse {
    private Long id;
    private int quantity;
    private DishResponse dishResponse;
    private Long order;
}
