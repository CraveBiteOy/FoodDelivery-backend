package com.fooddelivery.backend.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Models.Response.UserResponse;

@Component
public class UserMapper {

    @Autowired
    ModelMapper modelMapper;

    public UserResponse mapUserToResponse(Users user) {
      UserResponse res = modelMapper.map(user, UserResponse.class);
      // if(user.getCourier() != null) {
      //   res.setCourier(user.getCourier().getId());
      // }
      // if(user.getOwner() != null) {
      //   res.setOwner(user.getOwner().getId());
      // }
      // if(user.getCustomer() != null) {
      //   res.setCustomer(user.getCustomer().getId());
      // }
      return res;
    }
}
