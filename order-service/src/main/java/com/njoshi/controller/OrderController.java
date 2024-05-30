package com.njoshi.controller;

import com.njoshi.dto.OrderResponseDTO;
import com.njoshi.entity.Order;
import com.njoshi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService service;

    //Validation
    //logging
    //Exception handling
    @PostMapping
    public String placeNewOrder(@RequestBody Order order) {
        log.info("OrderController:: placeNewOrder: {}", order.toString());
        return service.placeAnOrder(order);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrder(@PathVariable String orderId) {
        log.info("OrderController:: getOrder: {}", orderId);
        OrderResponseDTO order = service.getOrder(orderId);
        log.info("OrderController:: getOrder response: {}", order.toString());
        return order;
    }


}
