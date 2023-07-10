package com.fooddelivery.backend.Service;

import java.util.List;

import com.fooddelivery.backend.Models.Owner;

public interface OwnerService {
    //require token from authenticated User;
    Owner getOwnerByAuthenticatedUser();
    Owner getOwnerById(Long id);
    List<Owner> getAllOwners();
}
