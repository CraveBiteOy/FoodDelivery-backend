package com.fooddelivery.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fooddelivery.backend.Mapper.CustomerMapper;
import com.fooddelivery.backend.Models.Response.CustomerResponse;
import com.fooddelivery.backend.Service.CustomerService;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerMapper customerMapper;

     // get customer by authenticated user or if the authenticated user does not have customer, the new customer will be created.
    @GetMapping("/customer/authenticatedUser")
    public ResponseEntity<CustomerResponse> getByAuthUser() {
        CustomerResponse res = customerMapper.mapCustomerToResponse(customerService.getByAuthenticatedUser());
        return new ResponseEntity<CustomerResponse>(res, HttpStatus.OK);
    }

      // get customer by id
    @GetMapping("/customer/id/{id}")
    public ResponseEntity<CustomerResponse> getbyId(@PathVariable Long id) {
        CustomerResponse res = customerMapper.mapCustomerToResponse(customerService.getById(id));
        return new ResponseEntity<CustomerResponse>(res, HttpStatus.OK);
    }
}
