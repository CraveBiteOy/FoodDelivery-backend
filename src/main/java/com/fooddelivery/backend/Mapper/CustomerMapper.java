package com.fooddelivery.backend.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.fooddelivery.backend.Models.Customer;
import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Models.Response.CustomerResponse;
import com.fooddelivery.backend.Models.Response.UserResponse;

@Component
public class CustomerMapper {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserMapper userMapper;

    public CustomerResponse mapCustomerToResponse(Customer customer) {
      CustomerResponse res = modelMapper.map(customer, CustomerResponse.class);
      res.setUser(userMapper.mapUserToResponse(customer.getUser()));
      return res;
    }
}
