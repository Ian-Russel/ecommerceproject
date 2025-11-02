package com.bautista.model;

import lombok.Data;

@Data
public class ReviewRequest {
    private Integer productId;
    private Integer rating;
    private String title;
    private String comment;
}