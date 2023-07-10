package com.fooddelivery.backend.Models.Response;
import com.fooddelivery.backend.Models.Enums.CourierStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourierResponse {
    private Long id;
    private UserResponse user;
    private CourierStatus status;
    private boolean available;
}
