package com.bautista.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private String productType;
    private String categoryName;
    private String subCategory;
    private String unitOfMeasure;
    private BigDecimal price;
    private String imageFile;
    private String additionalImages;
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
    private String status;
}