package com.fooddelivery.backend.Kafka.Consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fooddelivery.backend.Models.Response.OrderResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderConsumer {
    
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(
        topics = "order-customer",
        groupId = "group-order",
        containerFactory = "orderKafkaListenerContainerFactory"
    )
    public void consumeCustomerOrder(OrderResponse order) {
        log.info("customer order received: " + order.toString());

         // the customer subscribe this websocket link to keep tracking the order data in real time
         simpMessagingTemplate.convertAndSend("/order/" + order.getId(), order);
    }

    @KafkaListener(
        topics = "order-restaurant",
        groupId = "group-order",
        containerFactory = "orderKafkaListenerContainerFactory"
    )
    public void consumeRestaurantOrder(OrderResponse order) {
        log.info("restaurant order received: " + order.toString());

         // restaurant owner subscribe this websocket link to keep tracking the order data in real time
         simpMessagingTemplate.convertAndSend("/restaurant/" + order.getRestaurant().getId(), order);
    }

    @KafkaListener(
        topics = "order-courier",
        groupId = "group-order",
        containerFactory = "orderKafkaListenerContainerFactory"
    )
    public void consumeCourierOrder(OrderResponse order) {
        log.info("restaurant order received: " + order.toString());

        // courier subscribe this websocket link to get notified for the updating order
        simpMessagingTemplate.convertAndSend("/order/courier/" + order.getCourier().getId(), order);
    }
}
