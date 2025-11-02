package com.bautista.service;

import com.bautista.model.Review;
import com.bautista.model.ReviewRequest;
import com.bautista.model.ReviewSummary;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewRequest request, Integer userId);
    Review updateReview(Integer reviewId, Review review, Integer userId);
    void deleteReview(Integer reviewId, Integer userId);
    Review getReview(Integer reviewId);
    List<Review> getProductReviews(Integer productId);
    List<Review> getUserReviews(Integer userId);
    ReviewSummary getProductReviewSummary(Integer productId);
    boolean hasUserReviewedProduct(Integer productId, Integer userId);
    void markReviewHelpful(Integer reviewId, boolean helpful);
    void approveReview(Integer reviewId);
    void rejectReview(Integer reviewId);
    void adminDeleteReview(Integer reviewId);
    List<Review> getPendingReviews();
    List<Review> getAllReviews();
}