package com.bautista.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductSearchRequest {
    private String query;
    private String category;
    private String brand;
    private String color;
    private String gender;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String sortBy;
}