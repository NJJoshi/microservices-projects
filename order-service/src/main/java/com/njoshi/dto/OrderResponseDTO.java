package com.njoshi.dto;

import com.njoshi.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {
    private String message;
    private Order order;
    private PaymentDTO paymentResponse;
    private UserDTO userInfo;

}
