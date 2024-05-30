package com.njoshi.service;

import com.njoshi.entity.Payment;
import com.njoshi.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {
    @Autowired
    private PaymentRepository repository;

    public Payment getByOrderId(String orderId) {
        Payment payment = repository.findByOrderId(orderId);
        log.info("payment {}", payment.toString());
        return payment;
    }
}
