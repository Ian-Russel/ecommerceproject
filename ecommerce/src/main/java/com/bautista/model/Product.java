package com.bautista.model;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Product {
    private Integer id;
    private String name;
    private String description;
    private String categoryName;
    private String imageFile;
    private String unitOfMeasure;
    private BigDecimal price;

    // Add new fields for expansion
    private String productType;  // "footwear", "bag", "clothing"
    private String subCategory;
    private String brand;
    private String color;
    private String gender;

    private Integer stockQuantity;
    private String sku;

    private Integer discountPercentage;
    private Boolean isFeatured;
    private Boolean isNewArrival;
    private Boolean isBestSeller;

    private Double rating;
    private Integer reviewCount;

    // Flexible attributes (will be parsed from JSON in ProductData)
    private Object attributes;  // Can be Map<String, Object> or specific attribute classes

    private String status;
    private Date createdDate;
    private Date lastUpdated;
}