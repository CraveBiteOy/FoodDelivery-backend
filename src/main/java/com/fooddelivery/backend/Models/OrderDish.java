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

@Entity(name = "Order_dish")
@Table(name = "order_dish")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Min(value = 0, message = "price must be higher than 0")
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id", referencedColumnName = "id")
    private Dish dish;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private  Order order;

    public OrderDish(int quantity, Dish dish, Order order) {
        this.quantity = quantity;
        this.dish = dish;
        this.order = order;
    }

    
}
