package com.bautista.model;

import lombok.Data;
import java.util.Date;

@Data
public class Review {
    private Integer id;
    private Integer productId;
    private Integer userId;
    private Integer orderId;
    private Integer rating;
    private String title;
    private String comment;
    private Boolean verifiedPurchase;
    private Boolean isApproved;
    private String customerName;
    private String customerEmail;
    private Integer helpfulCount;
    private Integer notHelpfulCount;
    private Date createdAt;
    private Date updatedAt;
}