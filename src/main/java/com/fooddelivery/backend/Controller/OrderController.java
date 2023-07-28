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
import com.fooddelivery.backend.Models.Courier;
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
@RequestMapping("/api/orders")
public class OrderController {
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

    // get in-progressing orders  by restaurant Id
    @GetMapping("/inprogress/restaurant/{restaurantID}")
    public ResponseEntity<List<OrderResponse>> getActiveOrdersByRestaurant(@PathVariable Long restaurantID) {
        List<Order> orders = orderService.getByRestaurant(restaurantID); 
        List<OrderResponse> res = orders.stream().filter(order -> !order.getStatus().equals(OrderStatus.COMPLETED)).map(order -> orderMapper.mapOrderToResponse(order)).collect(Collectors.toList());
        return new ResponseEntity<List<OrderResponse>>(res, HttpStatus.OK);
    }

     // get completed orders  by restaurant Id
    @GetMapping("/completed/restaurant/{restaurantID}")
    public ResponseEntity<List<OrderResponse>> getCompletedOrdersByRestaurant(@PathVariable Long restaurantID) {
        List<Order> orders = orderService.getByRestaurant(restaurantID); 
        List<OrderResponse> res = orders.stream().filter(order -> order.getStatus().equals(OrderStatus.COMPLETED)).map(order -> orderMapper.mapOrderToResponse(order)).collect(Collectors.toList());
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
    public ResponseEntity<OrderResponse> createNewOrder(@RequestBody @Valid OrderRequest request) {
        Order order = orderService.create(request);
        OrderResponse res = orderMapper.mapOrderToResponse(order);
        System.out.println(order);
        System.out.println("/restaurant/" + res.getRestaurant().getId());

        // restaurant owner subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/restaurant/" + res.getRestaurant().getId(), res);

        // the customer subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/order/" + order.getId(), res);

        return new ResponseEntity<OrderResponse>(res, HttpStatus.CREATED);
    }

    // create new order from previous order
    @PostMapping("/reorder/previousOrder/{preOrderID}")
    public ResponseEntity<OrderResponse> createReOrder(@PathVariable Long preOrderID) {
        Order order = orderService.reOrder(preOrderID);
        OrderResponse res = orderMapper.mapOrderToResponse(order);

        // restaurant owner subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/restaurant/" + res.getRestaurant().getId(), res);

        // the customer subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/order/" + order.getId(), res);

        return new ResponseEntity<OrderResponse>(res, HttpStatus.CREATED);
    }
    
    //  // create new order
    // @PostMapping("/order")
    // public ResponseEntity<Order> createNewOrder(@RequestBody @Valid OrderRequest request) {
    //     //OrderResponse res = orderMapper.mapOrderToResponse(orderService.create(request));
    //     return new ResponseEntity<Order>(orderService.create(request), HttpStatus.CREATED);
    // }

    // owner accepts the order
    @PutMapping("/order/id/{id}/acceptByOwner")
    public ResponseEntity<OrderResponse> acceptOrderByOwner(@PathVariable Long id) {
        Order order = orderService.acceptOrderByOwner(id);
        OrderResponse res = orderMapper.mapOrderToResponse(order);
        Courier courier = order.getCourier();

        // restaurant owner subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/restaurant/" + res.getRestaurant().getId(), res);

       // the customer subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/order/" + order.getId(), res);

        if(courier != null) {
            // courier subscribe this websocket link to get notified for the updating order
            simpMessagingTemplate.convertAndSend("/order/courier/" + order.getCourier().getId(), res);
        }

        return new ResponseEntity<OrderResponse>(res, HttpStatus.OK);
    }

    // owner rejects the order
    @PutMapping("/order/id/{id}/rejectByOwner")
    public ResponseEntity<OrderResponse> rejectOrderByOwner(@PathVariable Long id) {
        Order order = orderService.rejectOrderByOwner(id);
        OrderResponse res = orderMapper.mapOrderToResponse(order);

        // restaurant owner subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/restaurant/" + res.getRestaurant().getId(), res);

        // the customer subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/order/" + order.getId(), res);

        return new ResponseEntity<OrderResponse>(res, HttpStatus.OK);
    }

     // owner finishes cooking for the order and the order is ready for being picked up
    @PutMapping("/order/id/{id}/finishCooking")
    public ResponseEntity<OrderResponse> finishCooking(@PathVariable Long id) {
        Order order = orderService.finishCookingByOwner(id);
        OrderResponse res = orderMapper.mapOrderToResponse(order);
        Courier courier = order.getCourier();

        // restaurant owner subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/restaurant/" + res.getRestaurant().getId(), res);

        // customer subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/order/" + order.getId(), res);

        if(courier != null) {
            // courier subscribe this websocket link to get notified for the updating order
            simpMessagingTemplate.convertAndSend("/order/courier/" + order.getCourier().getId(), res);
        }

        return new ResponseEntity<OrderResponse>(res, HttpStatus.OK);
    }

    // courier accept order
    @PutMapping("/order/id/{id}/acceptByCourier")
    public ResponseEntity<OrderResponse> acceptOrderByCourier(@PathVariable Long id) {
        Order order = orderService.acceptOrderByCourier(id);
        OrderResponse res = orderMapper.mapOrderToResponse(order);
        CourierResponse courierResponse = courierMapper.mapCourierToResponse(order.getCourier());
        
        // restaurant owner subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/restaurant/" + res.getRestaurant().getId(), res);

        // customer and courier subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/order/" + order.getId(), res);     
        
        // courier subscribe this websocket link to get notified for the updating order
        simpMessagingTemplate.convertAndSend("/order/courier/" + order.getCourier().getId(), res);

        return new ResponseEntity<OrderResponse>(res, HttpStatus.OK);
    }

    // courier reject order
    @PutMapping("/order/id/{id}/rejectByCourier")
    public ResponseEntity<OrderResponse> rejectOrderByCourier(@PathVariable Long id) {
        Order order = orderService.rejectOrderByCourier(id);
        OrderResponse res = orderMapper.mapOrderToResponse(order);

        // restaurant owner subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/restaurant/" + res.getRestaurant().getId(), res);

        return new ResponseEntity<OrderResponse>(res, HttpStatus.OK);
    }

    // order is picked up
    @PutMapping("/order/id/{id}/pickedUp")
    public ResponseEntity<OrderResponse> pickedUpOrder(@PathVariable Long id) {
        Order order = orderService.pickedUpOrderByCourier(id);
        OrderResponse res = orderMapper.mapOrderToResponse(order);
        CourierResponse courierResponse = courierMapper.mapCourierToResponse(order.getCourier());

        // restaurant owner subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/restaurant/" + res.getRestaurant().getId(), res);

        // customer  subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/order/" + order.getId(), res);

        // courier subscribe this websocket link to get notified for the updating order
        simpMessagingTemplate.convertAndSend("/order/courier/" + order.getCourier().getId(), res);

        return new ResponseEntity<OrderResponse>(res, HttpStatus.OK);
    }

    // order is completed
    @PutMapping("/order/id/{id}/completed")
    public ResponseEntity<OrderResponse> completedOrder(@PathVariable Long id) {
        Order order = orderService.completedOrderByCourier(id);
        OrderResponse res = orderMapper.mapOrderToResponse(order);
        CourierResponse courierResponse = courierMapper.mapCourierToResponse(order.getCourier());

        // restaurant owner subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/restaurant/" + res.getRestaurant().getId(), res);

        // customer and courier subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/order/" + order.getId(), res);

         // courier subscribe this websocket link to get notified for the updating order
        simpMessagingTemplate.convertAndSend("/order/courier/" + order.getCourier().getId(), res);


        return new ResponseEntity<OrderResponse>(res, HttpStatus.OK);
    }

    // // update the location for order after being picked up by the courier, the longitude and latitude request are gotten from the location of authenticated courier 
    // @PutMapping("/order/id/{id}/locationUpdate")
    // public ResponseEntity<OrderResponse> updateOrderLocation(@PathVariable Long id) {
    //     Order order = orderService.updateLocationOfTheOrder(id);
    //     OrderResponse res = orderMapper.mapOrderToResponse(order);
    //     CourierResponse courierResponse = courierMapper.mapCourierToResponse(order.getCourier());

    //      // restaurant owner subscribe this websocket link to keep tracking the order data in real time
    //     simpMessagingTemplate.convertAndSend("/restaurant/" + res.getRestaurant().getId(), res);

    //     // customer and courier subscribe this websocket link to keep tracking the order data in real time
    //     simpMessagingTemplate.convertAndSend("/order/" + order.getId(), res);

    //     // the courier subscribe this websocket link to keep tracking the real-time courier data
    //     simpMessagingTemplate.convertAndSend("/courier/" + order.getCourier().getId(), courierResponse);

    //     // courier subscribe this websocket link to get notified for the updating order
    //     simpMessagingTemplate.convertAndSend("/order/courier/" + order.getCourier().getId(), res);


    //     return new ResponseEntity<OrderResponse>(res, HttpStatus.OK);
    // }


     // update the location for order and the authenticated courier after being picked up by the courier, the longitude and latitude are adhered to the url pathvariables 
    @PutMapping("/order/id/{id}/longitude/{longitude}/latitude/{latitude}")
    public ResponseEntity<OrderResponse> updateOrderLocationAndAuthCourier(@PathVariable Long id, @PathVariable Double longitude, @PathVariable Double latitude) {
        Order order = orderService.updateLocationForTheOrderAndCourier(id, latitude, longitude);
        OrderResponse res = orderMapper.mapOrderToResponse(order);
        CourierResponse courierResponse = courierMapper.mapCourierToResponse(order.getCourier());

         // restaurant owner subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/restaurant/" + res.getRestaurant().getId(), res);

        // customer and courier subscribe this websocket link to keep tracking the order data in real time
        simpMessagingTemplate.convertAndSend("/order/" + order.getId(), res);

        // courier subscribe this websocket link to get notified for the updating order
        simpMessagingTemplate.convertAndSend("/order/courier/" + order.getCourier().getId(), res);


        return new ResponseEntity<OrderResponse>(res, HttpStatus.OK);
    }

}
