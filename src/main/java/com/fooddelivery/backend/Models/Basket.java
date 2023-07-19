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

@Entity(name = "Basket")
@Table(name = "basket", uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "restaurant_id"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Min(value = 0, message = "total must be higher than 0")
    @Column(name = "total", nullable = false)
    private Double total;

    @Min(value = 0, message = "quantity must be higher than 0")
    @Column(name = "quantity", nullable = false)
    private int quantity;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private  Restaurant restaurant;

    @JsonIgnore
    @OneToMany(mappedBy = "basket", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BasketDish> basketDishes = new ArrayList<>();

    public Basket(Customer customer, Restaurant restaurant) {
        this.customer = customer;
        this.restaurant = restaurant;
        this.total = 0.00;
        this.quantity = 0;
    }

    
}
