package com.fooddelivery.backend.Service.Implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.backend.Exception.EntityNotFoundException;
import com.fooddelivery.backend.Models.Courier;
import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Models.Enums.CourierStatus;
import com.fooddelivery.backend.Models.Enums.NavigationMode;
import com.fooddelivery.backend.Repository.CourierRepos;
import com.fooddelivery.backend.Repository.UserRepos;
import com.fooddelivery.backend.Service.CourierService;
import com.fooddelivery.backend.Service.UserService;

@Service
public class CourierServiceIml implements CourierService {

    @Autowired
    UserService userService;
    @Autowired
    CourierRepos courierRepos;
    @Autowired
    UserRepos userRepos;

    @Override
    public Courier getByAuthenticatedUser() {
        Users authUser = userService.getAuthUser();
        Optional<Courier> entity = courierRepos.findByUser(authUser);
        if(!entity.isPresent()) {
            Courier courier = new Courier(authUser, NavigationMode.BICYCLE);
            return courierRepos.save(courier);
        } else {
            Courier courier = entity.get();
            return courier;
        }
    }

    @Override
    public Courier getById(Long id) {
        Optional<Courier> entity = courierRepos.findById(id);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the courier not found");
        } else {
            Courier courier = entity.get();
            return courier;
        }
    }

    @Override
    public List<Courier> getOnlineAndAvailableCouriers() {
        List<Courier> list = courierRepos.findByAvailable(true).stream().filter(courier -> courier.getStatus().equals(CourierStatus.ONLINE)).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Courier> getOnlineAndAvailableCouriersFromMode(NavigationMode mode) {
        List<Courier> list = courierRepos.findByAvailable(true).stream().filter(courier -> courier.getStatus().equals(CourierStatus.ONLINE) && courier.getMode().equals(mode)).collect(Collectors.toList());
        return list;
    }

    @Override
    public Courier updateAvailableForAuthCourier(boolean available) {
        Courier courier = getByAuthenticatedUser();
        courier.setAvailable(available);;
        return courierRepos.save(courier);
    }

    @Override
    public Courier updateAvailableAndLocationForAuthCourier(boolean available, Double latitude, Double longitude) {
        Users authUser = userService.getAuthUser();
        authUser.setLatitude(latitude);
        authUser.setLongitude(longitude);
        userRepos.save(authUser);

        Courier courier = getByAuthenticatedUser();
        courier.setAvailable(available);
        return courierRepos.save(courier);
    }

    @Override
    public Courier updateLocationforAuthCourier(Double latitude, Double longitude) {
       Users authUser = userService.getAuthUser();
       authUser.setLatitude(latitude);
       authUser.setLongitude(longitude);
       userRepos.save(authUser);

       Courier courier = getByAuthenticatedUser();
       return courier;
    }

    @Override
    public Courier updateStatusForAuthCourier(CourierStatus status) {
        Courier courier = getByAuthenticatedUser();
        courier.setStatus(status);
        return courierRepos.save(courier);
    }
    
    
}
