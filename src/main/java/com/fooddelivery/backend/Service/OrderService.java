package com.fooddelivery.backend.Service;

import java.util.List;

import com.fooddelivery.backend.Models.Order;
import com.fooddelivery.backend.Models.Enums.OrderStatus;
import com.fooddelivery.backend.Models.Request.OrderRequest;

public interface OrderService {
    List<Order> getByRestaurant(Long restaurantID);
    List<Order> getByRestaurantAndStatus(Long restaurantID, OrderStatus status);
    List<Order> getByCustomer(Long customerID);
    List<Order> getByCustomerAndStatus(Long customerID, OrderStatus status);
    List<Order> getByCourier(Long courierID);
    List<Order> getByCourierAndStatus(Long courierID,  OrderStatus status);
    Order getByID(Long orderID);
    Order create(OrderRequest req);
    Order reOrder(Long preOrderID);
    Order acceptOrderByOwner(Long orderID);
    Order rejectOrderByOwner(Long orderID);
    Order finishCookingByOwner(Long orderID);
    Order acceptOrderByCourier(Long orderID);
    Order rejectOrderByCourier(Long orderID);
    Order pickedUpOrderByCourier(Long orderID);
    Order completedOrderByCourier(Long orderID);
    Order updateLocationOfTheOrder(Long orderID);

}
