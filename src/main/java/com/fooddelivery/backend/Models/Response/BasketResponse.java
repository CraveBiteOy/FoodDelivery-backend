package com.fooddelivery.backend.Models.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketResponse {
    private Long id;
    private int quantity;
    private Double total;
    private Long restaurant;
    private Long customer;

}
