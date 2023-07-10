package com.fooddelivery.backend.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.backend.Mapper.CourierMapper;
import com.fooddelivery.backend.Models.Courier;
import com.fooddelivery.backend.Models.Enums.CourierStatus;
import com.fooddelivery.backend.Models.Response.BasketResponse;
import com.fooddelivery.backend.Models.Response.CourierResponse;
import com.fooddelivery.backend.Service.CourierService;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {
    @Autowired
    CourierMapper courierMapper;
    @Autowired
    CourierService courierService;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    // get courier by id
    @GetMapping("/courier/id/{id}")
    public ResponseEntity<CourierResponse> getById(@PathVariable Long id) {
        CourierResponse res = courierMapper.mapCourierToResponse(courierService.getById(id));
        return new ResponseEntity<CourierResponse>(res, HttpStatus.OK);
    }
     // get courier by authenticated user
    @GetMapping("/courier/authenticated")
    public ResponseEntity<CourierResponse> getByAuthUser() {
        CourierResponse res = courierMapper.mapCourierToResponse(courierService.getByAuthenticatedUser());
        return new ResponseEntity<CourierResponse>(res, HttpStatus.OK);
    }

     // get list of available and online couriers
    @GetMapping("/online/available")
    public ResponseEntity<List<CourierResponse>> getListOfCouriers() {
        List<Courier> list = courierService.getOnlineAndAvailableCouriers();
        List<CourierResponse> res = list.stream().map(courier -> courierMapper.mapCourierToResponse(courier)).collect(Collectors.toList());
        return new ResponseEntity<List<CourierResponse>>(res, HttpStatus.OK);
    }

    // update status for authenticated courier
    @PutMapping("/courier/authenticated/status/{status}")
    public ResponseEntity<CourierResponse> updateStatus(@PathVariable CourierStatus status) {
        CourierResponse res = courierMapper.mapCourierToResponse(courierService.updateStatusForAuthCourier(status));
        simpMessagingTemplate.convertAndSend("/courier/" + res.getId(), res);
        return new ResponseEntity<CourierResponse>(res, HttpStatus.OK);
    }
    // update location for authenticated courier
    @PutMapping("/courier/authenticated/longitude/{longitude}/latitude/{latitude}")
    public ResponseEntity<CourierResponse> updateLocation(@PathVariable Double longitude, @PathVariable Double latitude) {
        CourierResponse res = courierMapper.mapCourierToResponse(courierService.updateLocationforAuthCourier(latitude, longitude));
        simpMessagingTemplate.convertAndSend("/courier/" + res.getId(), res);
        return new ResponseEntity<CourierResponse>(res, HttpStatus.OK);
    }

    // update location and availability for authenticated courier
    @PutMapping("/courier/authenticated/longitude/{longitude}/latitude/{latitude}/available/{available}")
    public ResponseEntity<CourierResponse> updateLocationAndAvailability(@PathVariable Double longitude, @PathVariable Double latitude, @PathVariable boolean available) {
        CourierResponse res = courierMapper.mapCourierToResponse(courierService.updateAvailableAndLocationForAuthCourier(available, latitude, longitude));
        simpMessagingTemplate.convertAndSend("/courier/" + res.getId(), res);
        return new ResponseEntity<CourierResponse>(res, HttpStatus.OK);
    }

    // update availability for authenticated courier
    @PutMapping("/courier/authenticated/available/{available}")
    public ResponseEntity<CourierResponse> updateAvailability( @PathVariable boolean available) {
        CourierResponse res = courierMapper.mapCourierToResponse(courierService.updateAvailableForAuthCourier(available));
        simpMessagingTemplate.convertAndSend("/courier/" + res.getId(), res);
        return new ResponseEntity<CourierResponse>(res, HttpStatus.OK);
    }
}
