package com.njoshi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.njoshi.dto.OrderResponseDTO;
import com.njoshi.dto.PaymentDTO;
import com.njoshi.dto.UserDTO;
import com.njoshi.entity.Order;
import com.njoshi.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

@Service
@RefreshScope
@Slf4j
public class OrderService {

    public static final String ORDER_SERVICE = "orderService";
    @Autowired
    private OrderRepository repository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${order.producer.topic.name}")
    private String topicName;

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    @Value("${microservice.payment-service.endpoints.fetchPaymentById.uri}")
    private String fetchPaymentUri;

    @Value("${microservice.user-service.endpoints.fetchUserById.uri}")
    private String fetchUserUri;

    @Value("${test.input}")
    private String testValue;

    public String placeAnOrder(Order order) {
        //save a copy in order-service DB
        log.info("placeAnOrder into kafka: {}", order.toString());
        order.setPurchaseDate(new Date());
        order.setOrderId(UUID.randomUUID().toString().split("-")[0]);
        repository.save(order);
        //send it to payment service using kafka
        try {
            kafkaTemplate.send(topicName, new ObjectMapper().writeValueAsString(order));
        } catch (JsonProcessingException e) {
            e.printStackTrace();//log statement log.error
        }
        return "Your order with (" + order.getOrderId() + ") has been placed ! we will notify once it will confirm";
    }

    @CircuitBreaker(name = ORDER_SERVICE, fallbackMethod = "getOrderDetails")
    public OrderResponseDTO getOrder(String orderId) {
        //own DB -> ORDER
        log.info("****** {}",testValue);
        log.info("fetchPaymentUri : {}  &&  fetchUserUri: {} ",fetchPaymentUri,fetchUserUri);
        Order order = repository.findByOrderId(orderId);
        //PAYMENT-> REST call payment-service
        PaymentDTO paymentDTO = restTemplate.getForObject(fetchPaymentUri + orderId, PaymentDTO.class);
        log.info("****** PAYMENT Rest call : {}",paymentDTO);
        //user-info-> rest call user-service
        UserDTO userDTO = restTemplate.getForObject(fetchUserUri + order.getUserId(), UserDTO.class);
        log.info("******* USER Rest call : {}",userDTO);
        return OrderResponseDTO.builder()
                .order(order)
                .paymentResponse(paymentDTO)
                .userInfo(userDTO)
                .build();

    }

    public OrderResponseDTO getOrderDetails(String orderId, Exception ex) {
        //you can call a DB
        //you can invoke external api
        return new OrderResponseDTO("FAILED", null, null, null);
    }


}
