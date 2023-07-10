package com.fooddelivery.backend.Service.Implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.backend.Exception.EntityNotFoundException;
import com.fooddelivery.backend.Models.Customer;
import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Repository.CustomerRepos;
import com.fooddelivery.backend.Repository.UserRepos;
import com.fooddelivery.backend.Service.CustomerService;
import com.fooddelivery.backend.Service.UserService;

@Service
public class CustomerServiceIml implements CustomerService {
    @Autowired
    UserService userService;
    @Autowired
    CustomerRepos customerRepos;
    @Autowired 
    UserRepos userRepos;
    
    @Override
    public Customer getByAuthenticatedUser() {
       Users authUser = userService.getAuthUser();
       Optional<Customer> entity = customerRepos.findByUser(authUser);
       if(!entity.isPresent()) {
            Customer customer = new Customer(authUser);
            return customerRepos.save(customer);
       } else {
            return entity.get();
       }
    }

    @Override
    public Customer getById(Long id) {
          Optional<Customer> entity = customerRepos.findById(id);
          if(!entity.isPresent()) {
               throw new EntityNotFoundException("the customer not found");
          } else {
               return entity.get();
          }
    }
}
