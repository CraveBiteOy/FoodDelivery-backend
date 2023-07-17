package com.fooddelivery.backend.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.backend.Exception.BadResultException;
import com.fooddelivery.backend.Exception.EntityNotFoundException;
import com.fooddelivery.backend.Models.Basket;
import com.fooddelivery.backend.Models.BasketDish;
import com.fooddelivery.backend.Models.Courier;
import com.fooddelivery.backend.Models.Customer;
import com.fooddelivery.backend.Models.Order;
import com.fooddelivery.backend.Models.OrderDish;
import com.fooddelivery.backend.Models.Owner;
import com.fooddelivery.backend.Models.Restaurant;
import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Models.Enums.NavigationMode;
import com.fooddelivery.backend.Models.Enums.OrderStatus;
import com.fooddelivery.backend.Models.Request.OrderRequest;
import com.fooddelivery.backend.Repository.BasketDishRepos;
import com.fooddelivery.backend.Repository.BasketRepos;
import com.fooddelivery.backend.Repository.OrderDishRepos;
import com.fooddelivery.backend.Repository.OrderRepos;
import com.fooddelivery.backend.Service.BasketDishService;
import com.fooddelivery.backend.Service.BasketService;
import com.fooddelivery.backend.Service.CourierService;
import com.fooddelivery.backend.Service.CustomerService;
import com.fooddelivery.backend.Service.OrderService;
import com.fooddelivery.backend.Service.OwnerService;
import com.fooddelivery.backend.Service.RestaurantService;
import com.fooddelivery.backend.Service.UserService;
import com.fooddelivery.backend.Utils.DistanceCoding;
import com.fooddelivery.backend.Utils.GeoCoding;

@Service
public class OrderServiceIml implements OrderService{
    private final double fixedFeeForPickupOrder = 3.00;
    private final double fixedFeeForOneKilometer = 1.00;
    private final double fixedTimeForOneKilometer = 5;
    private final double navigationModeDistance = 5;

    @Autowired
    OrderRepos orderRepos;
    @Autowired
    UserService userService;
    @Autowired
    BasketService basketService;
    @Autowired
    BasketDishService basketDishService;
    @Autowired
    DistanceCoding distanceCoding;
    @Autowired
    CustomerService customerService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    OwnerService ownerService;
    @Autowired
    CourierService courierService;
    @Autowired
    GeoCoding geoCoding;
    @Autowired
    OrderDishRepos orderDishRepos;
    @Autowired
    BasketDishRepos basketDishRepos;
    @Autowired
    BasketRepos basketRepos;
  

    @Override
    public Order create(OrderRequest req) {
        Basket basket = basketService.getById(req.getBasketID());
        Customer customer = basket.getCustomer();
        Restaurant restaurant = basket.getRestaurant();
        Users authUser = userService.getAuthUser();

        if(authUser.getId() != customer.getUser().getId()) {
            throw new BadResultException("is not authorized to place an order because the authenticated user is not the customer of the basket");
        }

        // get the longitude and latitude of the customer address, zipcode, and city if the customer wants to deliver the food the a different location rather than customer's current location
        if(req.getToLatitude() == null && req.getToLongitude() == null) {
            req = geoCoding.geoLocationEncode(req);
        }

        // create new order 
        Order order = new Order(customer, restaurant, OrderStatus.NEW, basket.getTotal(), basket.getQuantity(), req.getToLongitude(), req.getToLatitude());
        if(req.getNote() != null) {
            order.setNote(req.getNote());
        }
        
        order.setFromLatitude(restaurant.getLatitude());
        order.setFromLongitude(restaurant.getLongitude());

        // calculate the delivery fee;

       
        double distance = distanceCoding.distanceCalculator(order.getFromLatitude(), order.getToLatitude(), order.getFromLongitude(), order.getToLongitude());
        order.setDeliveryFee(fixedFeeForPickupOrder + (distance * fixedFeeForOneKilometer));
        order.setFinalPrice(order.getTotal() + order.getDeliveryFee());
        order.setD2Distance(distance);

        int dropOffTime =  (int) Math.round(distance * fixedTimeForOneKilometer);
        // order.setTotalTime(restaurant.getCookingTime() + (int) Math.round(distance * fixedTimeForOneKilometer));
        order.setTotalTime(restaurant.getCookingTime() + dropOffTime);
        order.setDropOffTime(dropOffTime);
        order.setPickedupTime(restaurant.getCookingTime());
        final Order finalOrder = orderRepos.save(order);

        // create orderDishes from basketDishes in the basket
        // remove basketDishes 
        List<BasketDish> basketDishes = basketDishService.getByBasket(basket.getId());
        basketDishes.forEach(basketDish -> {
            if(basketDish.getQuantity() > 0) {
                // create orderDish from basketDish in the basket
                OrderDish orderDish = new OrderDish(basketDish.getQuantity(), basketDish.getDish(), finalOrder);
                orderDishRepos.save(orderDish);

                basketDishRepos.delete(basketDish);
            }
        });

        // and clear the basket
        basket.setQuantity(0);
        basket.setTotal(0.00);
        basketRepos.save(basket);

        return finalOrder;
    }


    // the owner accept the order, and the server simultaneously matches the courier for the server
    @Override
    public Order acceptOrderByOwner(Long orderID) {
        Order order = getAndCheckOrderForOwner(orderID, OrderStatus.NEW);
        order.setStatus(OrderStatus.COOKING);
        Courier courier = reachCourier(order);
        order.setCourier(courier);
        order = orderRepos.save(order);
        return order;
    }

    @Override
    public Order rejectOrderByOwner(Long orderID) {
        Order order = getAndCheckOrderForOwner(orderID, OrderStatus.NEW);
        order.setStatus(OrderStatus.OWNER_REJECTED);
        order = orderRepos.save(order);
        return order;
    }

    // after the order cooking is finished and is ready for being picked up
    @Override
    public Order finishCookingByOwner(Long orderID) {
        Order order = getAndCheckOrderForOwner(orderID, OrderStatus.COOKING);
        order.setStatus(OrderStatus.READY_FOR_PICKUP);
        // Courier courier = reachCourier(order);
        // order.setCourier(courier);
        order = orderRepos.save(order);
        return order;
    }

    @Override
    public Order acceptOrderByCourier(Long orderID) {
        Order order = getByID(orderID);
        Courier courier = courierService.getByAuthenticatedUser();

        // update available status for courier
        courier = courierService.updateAvailableForAuthCourier(false);

        if(courier.getId() != order.getCourier().getId()) {
            throw new BadResultException("the authenticated courier is not authorized to accept the order");
        }
        if(!order.getStatus().equals(OrderStatus.READY_FOR_PICKUP)) {
            throw new BadResultException("the order status is wrong");
        }
        order.setStatus(OrderStatus.COURIER_ACCEPTED);
        orderRepos.save(order);
        return order;
    }

    // after receiving the request for rejecting the order from the current courier, the server must find out the new courier for the order and notify the order (accepting or rejecting notification) to the new courier 
    @Override
    public Order rejectOrderByCourier(Long orderID) {
        Order order = getByID(orderID);
        Courier courier = courierService.getByAuthenticatedUser();
        if(courier.getId() != order.getCourier().getId()) {
            throw new BadResultException("the authenticated courier is not authorized to reject the order");
        }
        if(!order.getStatus().equals(OrderStatus.READY_FOR_PICKUP)) {
            throw new BadResultException("the order status is wrong");
        }
        Courier newCourier = reachCourier(order);
        order.setCourier(newCourier);
        order = orderRepos.save(order);
        return order;
    }

    @Override
    public Order pickedUpOrderByCourier(Long orderID) {
        Order order = getByID(orderID);
        Courier courier = courierService.getByAuthenticatedUser();
        if(courier.getId() != order.getCourier().getId()) {
            throw new BadResultException("the authenticated courier is not authorized to pick up the order");
        }
         if(!order.getStatus().equals(OrderStatus.COURIER_ACCEPTED)) {
            throw new BadResultException("the order status is wrong");
        }
        
        // calculate the distace between restaurant and courier
        double distance = distanceCoding.distanceCalculator(order.getFromLatitude(), courier.getUser().getLatitude(), order.getFromLongitude(), courier.getUser().getLongitude());
        if(distance < 0.5) {
            order.setStatus(OrderStatus.PICKED_UP);
            return orderRepos.save(order);
        } else {
            throw new BadResultException("cannot make picked-up confirmation");
        }
    }

    @Override
    public Order completedOrderByCourier(Long orderID) {
        Order order = getByID(orderID);
        Courier courier = courierService.getByAuthenticatedUser();
        if(courier.getId() != order.getCourier().getId()) {
            throw new BadResultException("the authenticated courier is not authorized to pick up the order");
        }
        if(!order.getStatus().equals(OrderStatus.PICKED_UP)) {
            throw new BadResultException("the order status is wrong");
        }
        
        // calculate the distace between customer and courier
        double distance = distanceCoding.distanceCalculator(order.getToLatitude(), courier.getUser().getLatitude(), order.getToLongitude(), courier.getUser().getLongitude());
        if(distance < 0.5) {
            order.setStatus(OrderStatus.COMPLETED);
            
            // update available status for courier
            courierService.updateAvailableForAuthCourier(true);
            return orderRepos.save(order);
        } else {
            throw new BadResultException("cannot make completed confirmation");
        }
    }

    @Override
    public Order updateLocationOfTheOrder(Long orderID) {
        Order order = getByID(orderID);
        Courier courier = courierService.getByAuthenticatedUser();
        if(courier.getId() != order.getCourier().getId()) {
            throw new BadResultException("the authenticated courier is not authorized to pick up the order");
        }
        if(!order.getStatus().equals(OrderStatus.PICKED_UP)) {
            throw new BadResultException("cannot update the order status before the stage of being picked up");
        }
        order.setFromLatitude(courier.getUser().getLatitude());
        order.setFromLongitude(courier.getUser().getLongitude());
        orderRepos.save(order);
        return order;
    }


    @Override
    public List<Order> getByCourier(Long courierID) {
        Courier courier = courierService.getById(courierID);
        List<Order> orders = orderRepos.findByCourier(courier);
        return orders;
    }

    @Override
    public List<Order> getByCourierAndStatus(Long courierID, OrderStatus status) {
        Courier courier = courierService.getById(courierID);
        List<Order> orders = orderRepos.findByCourierAndStatus(courier, status);
        return orders;
    }

    @Override
    public List<Order> getByCustomer(Long customerID) {
        Customer customer = customerService.getById(customerID);
        List<Order> orders = orderRepos.findByCustomer(customer);
        return orders;
    }

    @Override
    public List<Order> getByCustomerAndStatus(Long customerID, OrderStatus status) {
       Customer customer = customerService.getById(customerID);
        List<Order> orders = orderRepos.findByCustomerAndStatus(customer, status);
        return orders;
    }

    @Override
    public Order getByID(Long orderID) {
        Optional<Order> entity = orderRepos.findById(orderID);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the order not found");
        }
        return entity.get();
    }

    @Override
    public List<Order> getByRestaurant(Long restaurantID) {
        Restaurant restaurant = restaurantService.getById(restaurantID);
        List<Order> orders = orderRepos.findByRestaurant(restaurant);
        return orders;
    }

    @Override
    public List<Order> getByRestaurantAndStatus(Long restaurantID, OrderStatus status) {
        Restaurant restaurant = restaurantService.getById(restaurantID);
        List<Order> orders = orderRepos.findByRestaurantAndStatus(restaurant, status);
        return orders;
    }

   
    // check the authenticated user whether he is the owner or not
    private Order getAndCheckOrderForOwner(Long orderID, OrderStatus currentStatus) {
        Order order = getByID(orderID);
        Users authUser = userService.getAuthUser();
        Restaurant restaurant = order.getRestaurant();
        Owner owner = restaurant.getOwner();
        if(authUser.getId() != owner.getUser().getId()) {
            throw new BadResultException("are not authorized to make acceptance");
        }
        if(!order.getStatus().equals(currentStatus)) {
            throw new BadResultException("the order status is wrong");
        }
        return order;
    }

    // find the active and available courier whose location is closest to the restaurant to carry out the order after the order's cooking stage is done
    private Courier reachCourier(Order order) {
        List<Courier> couriers;
        
        if(order.getD2Distance() > navigationModeDistance) {
            couriers = courierService.getOnlineAndAvailableCouriersFromMode(NavigationMode.CAR);
        } else {
            couriers = courierService.getOnlineAndAvailableCouriersFromMode(NavigationMode.BICYCLE);
        }
        System.out.println(couriers);
        if (couriers.size() == 0) {
            throw new BadResultException("there is no available courier for the order");
        }
        else if (couriers.size() == 1) {
           return couriers.get(0);
        } 
            Double longitude = order.getFromLongitude();
            Double latitude = order.getFromLatitude();
            Courier courier;
            double shortestDistance;
            courier = couriers.get(0);
            shortestDistance = distanceCoding.distanceCalculator(latitude, courier.getUser().getLatitude(), longitude, courier.getUser().getLongitude());

            // comparing the distace to restaurant amongst couriers 
            for(int i = 1; i < couriers.size() - 1; i++) {
                Courier courier2 = couriers.get(i);
                double distance = distanceCoding.distanceCalculator(latitude, courier2.getUser().getLatitude(), longitude, courier2.getUser().getLongitude());

                if(distance < shortestDistance) {
                    shortestDistance = distance;
                    courier = courier2;
                }
            }
            if (shortestDistance <= 20) {
                return courier;
            } else {
                System.out.println(courier);
                System.out.println(shortestDistance);
                throw new BadResultException("there is no available courier for the order");
            } 
          
    }
    
}
