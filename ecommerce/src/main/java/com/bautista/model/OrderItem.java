package com.bautista.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItem {
    private Integer id;
    private Integer productId;
    private String productName;
    private String productImage;
    private BigDecimal productPrice;
    private Integer quantity;
    private BigDecimal subtotal;
    private String productColor;
    private String productSize;
    private String productBrand;
}