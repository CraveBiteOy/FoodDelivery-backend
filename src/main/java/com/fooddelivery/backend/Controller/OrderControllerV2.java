package com.fooddelivery.backend.Controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.backend.Mapper.CourierMapper;
import com.fooddelivery.backend.Mapper.OrderMapper;
import com.fooddelivery.backend.Models.Order;
import com.fooddelivery.backend.Models.OrderDish;
import com.fooddelivery.backend.Models.Enums.OrderStatus;
import com.fooddelivery.backend.Models.Request.OrderRequest;
import com.fooddelivery.backend.Models.Response.CourierResponse;
import com.fooddelivery.backend.Models.Response.OrderDishResponse;
import com.fooddelivery.backend.Models.Response.OrderResponse;
import com.fooddelivery.backend.Service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/orders")
public class OrderControllerV2 {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    CourierMapper courierMapper;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;


    // get order by  order Id
    @GetMapping("/order/id/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        OrderResponse res = orderMapper.mapOrderToResponse(orderService.getByID(id));
        return new ResponseEntity<OrderResponse>(res, HttpStatus.OK);
    }

    // get order  by restaurant Id
    @GetMapping("/restaurant/{restaurantID}")
    public ResponseEntity<List<OrderResponse>> getByRestaurant(@PathVariable Long restaurantID) {
        List<Order> orders = orderService.getByRestaurant(restaurantID); 
        List<OrderResponse> res = orders.stream().map(order -> orderMapper.mapOrderToResponse(order)).collect(Collectors.toList());
        return new ResponseEntity<List<OrderResponse>>(res, HttpStatus.OK);
    }

    // get order  by restaurant Id and order status
    @GetMapping("/restaurant/{restaurantID}/status/{status}")
    public ResponseEntity<List<OrderResponse>> getbyRestaurantAndStatus(@PathVariable Long restaurantID, @PathVariable OrderStatus status) {
        List<Order> orders = orderService.getByRestaurantAndStatus(restaurantID, status); 
        List<OrderResponse> res = orders.stream().map(order -> orderMapper.mapOrderToResponse(order)).collect(Collectors.toList());
        return new ResponseEntity<List<OrderResponse>>(res, HttpStatus.OK);
    }

     // get order  by customer Id
    @GetMapping("/customer/{customerID}")
    public ResponseEntity<List<OrderResponse>> getByCustomer(@PathVariable Long customerID) {
        List<Order> orders = orderService.getByCustomer(customerID); 
        List<OrderResponse> res = orders.stream().map(order -> orderMapper.mapOrderToResponse(order)).collect(Collectors.toList());
        return new ResponseEntity<List<OrderResponse>>(res, HttpStatus.OK);
    }

    // get order  by customer Id and order status
    @GetMapping("/customer/{customerID}/status/{status}")
    public ResponseEntity<List<OrderResponse>> getByCustomerAndStatus(@PathVariable Long customerID, @PathVariable OrderStatus status) {
        List<Order> orders = orderService.getByCustomerAndStatus(customerID, status); 
        List<OrderResponse> res = orders.stream().map(order -> orderMapper.mapOrderToResponse(order)).collect(Collectors.toList());
        return new ResponseEntity<List<OrderResponse>>(res, HttpStatus.OK);
    }

      // get order  by courier Id
    @GetMapping("/courier/{courierID}")
    public ResponseEntity<List<OrderResponse>> getBycourier(@PathVariable Long courierID) {
        List<Order> orders = orderService.getByCourier(courierID); 
        List<OrderResponse> res = orders.stream().map(order -> orderMapper.mapOrderToResponse(order)).collect(Collectors.toList());
        return new ResponseEntity<List<OrderResponse>>(res, HttpStatus.OK);
    }

    // get order  by courier Id and order status
    @GetMapping("/courier/{courierID}/status/{status}")
    public ResponseEntity<List<OrderResponse>> getBycourierAndStatus(@PathVariable Long courierID, @PathVariable OrderStatus status) {
        List<Order> orders = orderService.getByCourierAndStatus(courierID, status); 
        List<OrderResponse> res = orders.stream().map(order -> orderMapper.mapOrderToResponse(order)).collect(Collectors.toList());
        return new ResponseEntity<List<OrderResponse>>(res, HttpStatus.OK);
    }

    
     // create new order
    @PostMapping("/order")
    public ResponseEntity<Order> createNewOrder(@RequestBody @Valid OrderRequest request) {
        //OrderResponse res = orderMapper.mapOrderToResponse(orderService.create(request));
        return new ResponseEntity<Order>(orderService.create(request), HttpStatus.CREATED);
    }

    // owner accepts the order
    @PutMapping("/order/id/{id}/acceptByOwner")
    public ResponseEntity<Order> acceptOrderByOwner(@PathVariable Long id) {
        Order order = orderService.acceptOrderByOwner(id);

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    // owner rejects the order
    @PutMapping("/order/id/{id}/rejectByOwner")
    public ResponseEntity<Order> rejectOrderByOwner(@PathVariable Long id) {
        Order order = orderService.rejectOrderByOwner(id);

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

     // owner finishes cooking for the order and the order is ready for being picked up
    @PutMapping("/order/id/{id}/finishCooking")
    public ResponseEntity<Order> finishCooking(@PathVariable Long id) {
        Order order = orderService.finishCookingByOwner(id);

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    // courier accept order
    @PutMapping("/order/id/{id}/acceptByCourier")
    public ResponseEntity<Order> acceptOrderByCourier(@PathVariable Long id) {
        Order order = orderService.acceptOrderByCourier(id);

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    // courier reject order
    @PutMapping("/order/id/{id}/rejectByCourier")
    public ResponseEntity<Order> rejectOrderByCourier(@PathVariable Long id) {
        Order order = orderService.rejectOrderByCourier(id);
    
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    // order is picked up
    @PutMapping("/order/id/{id}/pickedUp")
    public ResponseEntity<Order> pickedUpOrder(@PathVariable Long id) {
        Order order = orderService.pickedUpOrderByCourier(id);

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    // order is completed
    @PutMapping("/order/id/{id}/completed")
    public ResponseEntity<Order> completedOrder(@PathVariable Long id) {
        Order order = orderService.completedOrderByCourier(id);

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    // update the location for order after being picked up by the courier, the longitude and latitude request are gotten from the location of authenticated courier 
    @PutMapping("/order/id/{id}/locationUpdate")
    public ResponseEntity<Order> updateOrderLocation(@PathVariable Long id) {
        Order order = orderService.updateLocationOfTheOrder(id);

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

}
