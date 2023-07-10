package com.fooddelivery.backend.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.backend.Exception.EntityNotFoundException;
import com.fooddelivery.backend.Models.Owner;
import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Repository.OwnerRepos;
import com.fooddelivery.backend.Service.OwnerService;
import com.fooddelivery.backend.Service.UserService;

@Service
public class OwnerServiceIml implements OwnerService {
    @Autowired
    OwnerRepos ownerRepos;
    @Autowired
    UserService userService;

    @Override
    public List<Owner> getAllOwners() {
       return ownerRepos.findAll();
    }

    // if the authenticated use does not have the owner, create new owner
    @Override
    public Owner getOwnerByAuthenticatedUser() {
       Users authUser = userService.getAuthUser();
       Optional<Owner> entity = ownerRepos.findByUser(authUser);
       if(!entity.isPresent()) {
        Owner owner = new Owner(authUser);
        return ownerRepos.save(owner);
       } else {
        Owner owner = entity.get();
        return owner;
       }
    }

    @Override
    public Owner getOwnerById(Long id) {
      Optional<Owner> entity = ownerRepos.findById(id);
      if(!entity.isPresent()) {
         throw new EntityNotFoundException("the owner not found");
      } 
         Owner owner = entity.get();
         return owner;
      
    }
    
}
