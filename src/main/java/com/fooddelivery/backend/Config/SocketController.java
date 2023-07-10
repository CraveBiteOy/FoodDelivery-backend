package com.fooddelivery.backend.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class SocketController {
  
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
   
    @Autowired
    SocketService socketService;

    @MessageMapping("/test")
    public void testSocket(@Payload String message) {
        System.out.println(message + " , successfully test it");
        simpMessagingTemplate.convertAndSend("/testTopic", message);
    }

   


}
