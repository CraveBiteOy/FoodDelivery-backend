package com.fooddelivery.backend.Models;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fooddelivery.backend.Models.Enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Basket_dish")
@Table(name = "basket_dish", uniqueConstraints = @UniqueConstraint(columnNames = {"dish_id", "basket_id"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BasketDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Min(value = 0, message = "quantity must be higher than 0")
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id", referencedColumnName = "id")
    private Dish dish;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "basket_id", referencedColumnName = "id")
    private  Basket basket;

    public BasketDish( int quantity, Dish dish, Basket basket) {
        this.quantity = quantity;
        this.dish = dish;
        this.basket = basket;
    }


}
