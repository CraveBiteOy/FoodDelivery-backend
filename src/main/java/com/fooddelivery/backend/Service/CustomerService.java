package com.fooddelivery.backend.Service;

import com.fooddelivery.backend.Models.Customer;

public interface CustomerService {
    Customer getByAuthenticatedUser();
    Customer getById(Long id);
}
