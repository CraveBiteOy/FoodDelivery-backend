package com.fooddelivery.backend.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.fooddelivery.backend.Models.Owner;
import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Models.Response.OwnerResponse;
import com.fooddelivery.backend.Models.Response.UserResponse;

@Component
public class OwnerMapper {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserMapper userMapper;

    public OwnerResponse mapOwnerToResponse(Owner owner) {
      // ModelMapper modelMapper = new ModelMapper();
      // TypeMap<Owner, OwnerResponse> typeMap = modelMapper.createTypeMap(Owner.class, OwnerResponse.class);
      // typeMap.addMappings(mapper -> {
      //   mapper.map(src -> userMapper.mapUserToResponse(src.getUser()), OwnerResponse::setUser);
      // });
      OwnerResponse res = modelMapper.map(owner, OwnerResponse.class);
      System.out.println(res);
      // res.setUser(userMapper.mapUserToResponse(owner.getUser()));
      return res;
    }
}
