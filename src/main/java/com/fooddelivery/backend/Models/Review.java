package com.fooddelivery.backend.Models;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Review")
@Table(name = "review", uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "restaurant_id"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "rate", nullable = false)
    @Min(value = 1, message = "rate is larger than or equal to 1")
    @Max(value = 5, message = "rate is lesser than or equal to 5")
    private int rate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private  Restaurant restaurant;

     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;


    public Review( int rate, Customer customer, Restaurant restaurant) {
        this.rate = rate;
        this.customer = customer;
        this.restaurant = restaurant;
    }

    
}
