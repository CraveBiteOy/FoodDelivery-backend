package com.fooddelivery.backend.Models.Response;
import com.fooddelivery.backend.Models.Enums.Role;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerResponse {
    private Long id;
    private UserResponse user;
    @Override
    public String toString() {
        return "OwnerResponse [id=" + id + ", user=" + user + "]";
    }

    
}
