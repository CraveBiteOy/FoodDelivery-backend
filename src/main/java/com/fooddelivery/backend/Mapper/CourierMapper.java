package com.fooddelivery.backend.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fooddelivery.backend.Models.Courier;
import com.fooddelivery.backend.Models.Response.CourierResponse;

@Component
public class CourierMapper {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserMapper userMapper;

    public CourierResponse mapCourierToResponse(Courier courier) {
        CourierResponse res = modelMapper.map(courier, CourierResponse.class);
        res.setUser(userMapper.mapUserToResponse(courier.getUser()));
        return res;
    }
}
