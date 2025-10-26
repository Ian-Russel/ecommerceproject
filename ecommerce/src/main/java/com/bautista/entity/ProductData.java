package com.bautista.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "product_data")
public class ProductData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "sub_category")
    private String subCategory;

    @Column(name = "unit_of_measure")
    private String unitOfMeasure;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "image_file")
    private String imageFile;

    @Column(name = "additional_images", length = 1000)
    private String additionalImages;

    @Column(name = "brand")
    private String brand;

    @Column(name = "color")
    private String color;

    @Column(name = "gender")
    private String gender;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "sku", unique = true, length = 100)
    private String sku;

    @Column(name = "discount_percentage")
    private Integer discountPercentage;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @Column(name = "is_new_arrival")
    private Boolean isNewArrival;

    @Column(name = "is_best_seller")
    private Boolean isBestSeller;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "attributes", columnDefinition = "JSON")
    private String attributes;

    @Column(name = "specifications", columnDefinition = "JSON")
    private String specifications;

    @Column(name = "status")
    private String status;  // "Active", "Inactive", "Out of Stock", "Discontinued"

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "last_updated")
    private Date lastUpdated;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "created")
    private Date created;

    @PrePersist
    public void prePersist() {
        if (this.stockQuantity == null) {
            this.stockQuantity = 0;
        }
        if (this.discountPercentage == null) {
            this.discountPercentage = 0;
        }
        if (this.isFeatured == null) {
            this.isFeatured = false;
        }
        if (this.isNewArrival == null) {
            this.isNewArrival = false;
        }
        if (this.isBestSeller == null) {
            this.isBestSeller = false;
        }
        if (this.rating == null) {
            this.rating = 0.0;
        }
        if (this.reviewCount == null) {
            this.reviewCount = 0;
        }
        if (this.status == null) {
            this.status = "Active";
        }
    }
}