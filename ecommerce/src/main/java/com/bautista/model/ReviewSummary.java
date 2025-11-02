package com.bautista.model;

import lombok.Data;

@Data
public class ReviewSummary {
    private Integer productId;
    private Double averageRating;
    private Long totalReviews;
    private Long fiveStarCount;
    private Long fourStarCount;
    private Long threeStarCount;
    private Long twoStarCount;
    private Long oneStarCount;
}