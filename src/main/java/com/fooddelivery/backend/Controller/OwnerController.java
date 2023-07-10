package com.fooddelivery.backend.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.backend.Mapper.OwnerMapper;
import com.fooddelivery.backend.Models.Owner;
import com.fooddelivery.backend.Models.Response.OwnerResponse;
import com.fooddelivery.backend.Models.Response.UserResponse;
import com.fooddelivery.backend.Service.OwnerService;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    @Autowired
    OwnerService ownerService;
    @Autowired
    OwnerMapper ownerMapper;

    // get owner by by owner Id
    @GetMapping("/owner/id/{id}")
    public ResponseEntity<OwnerResponse> getById(@PathVariable Long id) {
        OwnerResponse res = ownerMapper.mapOwnerToResponse(ownerService.getOwnerById(id));
        return new ResponseEntity<OwnerResponse>(res, HttpStatus.OK);
    }

    //  @GetMapping("/owner/id/{id}")
    // public ResponseEntity<Owner> getById(@PathVariable Long id) {
    //     OwnerResponse res = ownerMapper.mapOwnerToResponse(ownerService.getOwnerById(id));
    //     return new ResponseEntity<Owner>(ownerService.getOwnerById(id), HttpStatus.OK);
    // }

    // get owner by authenticated user or if the authenticated user does not have owner, the new owner will be created.
    @GetMapping("/owner/authenticatedUser")
    public ResponseEntity<OwnerResponse> getByAuthUser() {
        OwnerResponse res = ownerMapper.mapOwnerToResponse(ownerService.getOwnerByAuthenticatedUser());
        return new ResponseEntity<OwnerResponse>(res, HttpStatus.OK);
    }

    // return the list of all owners
    @GetMapping("/all")
    public ResponseEntity<List<OwnerResponse>> getListOfOwners() {
        List<Owner> list = ownerService.getAllOwners();
        List<OwnerResponse> res = list.stream().map(owner -> ownerMapper.mapOwnerToResponse(owner)).collect(Collectors.toList());
        
        return new ResponseEntity<List<OwnerResponse>>(res, HttpStatus.OK);
    }
}
