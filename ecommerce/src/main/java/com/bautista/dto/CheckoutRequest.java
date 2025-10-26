package com.bautista.dto;

import com.bautista.model.OrderItem;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CheckoutRequest {
    private Integer userId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String shippingAddress;
    private String shippingCity;
    private String shippingProvince;
    private String shippingPostalCode;
    private BigDecimal totalAmount;
    private Integer totalItems;
    private String paymentMethod;
    private String notes;
    private List<OrderItem> orderItems;
}