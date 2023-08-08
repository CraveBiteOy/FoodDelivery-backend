package com.fooddelivery.backend.Kafka.Producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fooddelivery.backend.Models.Response.OrderResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderProducer {
    @Qualifier("OrderProducerFactory")
   private final KafkaTemplate<String, OrderResponse> orderKafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderResponse> orderKafkaTemplate) {
        this.orderKafkaTemplate = orderKafkaTemplate;
    }

    public OrderResponse sendToCustomer(OrderResponse order) {
        log.info("order sent to customer: " + order.toString());
        orderKafkaTemplate.send("order-customer", order);
        return order;
    } 
    public OrderResponse sendToRestaurant(OrderResponse order) {
        log.info("order sent to restaurant" + order.toString());
        orderKafkaTemplate.send("order-restaurant", order);
        return order;
    }
    public OrderResponse sendToCourier(OrderResponse order) {
        log.info("order sent to courier" + order.toString());
        orderKafkaTemplate.send("order-courier", order);
        return order;
    }
   
}
