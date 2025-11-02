package com.bautista.controller;

import com.bautista.model.Review;
import com.bautista.model.ReviewRequest;
import com.bautista.model.ReviewSummary;
import com.bautista.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/reviews")
@Slf4j
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(
            @RequestBody ReviewRequest request,
            @RequestHeader(value = "userId", required = false) Integer userId) {
        try {
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("User must be logged in to create a review");
            }

            log.info("Creating review for product {} by user {}", request.getProductId(), userId);
            Review review = reviewService.createReview(request, userId);
            return ResponseEntity.ok(review);
        } catch (RuntimeException ex) {
            log.error("Failed to create review: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            log.error("Error creating review: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the review");
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductReviews(@PathVariable Integer productId) {
        try {
            log.info("Getting reviews for product {}", productId);
            List<Review> reviews = reviewService.getProductReviews(productId);
            return ResponseEntity.ok(reviews);
        } catch (Exception ex) {
            log.error("Error getting product reviews: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve reviews");
        }
    }

    @GetMapping("/product/{productId}/summary")
    public ResponseEntity<?> getProductReviewSummary(@PathVariable Integer productId) {
        try {
            log.info("Getting review summary for product {}", productId);
            ReviewSummary summary = reviewService.getProductReviewSummary(productId);
            return ResponseEntity.ok(summary);
        } catch (Exception ex) {
            log.error("Error getting review summary: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve review summary");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserReviews(@PathVariable Integer userId) {
        try {
            log.info("Getting reviews for user {}", userId);
            List<Review> reviews = reviewService.getUserReviews(userId);
            return ResponseEntity.ok(reviews);
        } catch (Exception ex) {
            log.error("Error getting user reviews: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve user reviews");
        }
    }

    @GetMapping("/product/{productId}/user/{userId}/check")
    public ResponseEntity<?> hasUserReviewedProduct(
            @PathVariable Integer productId,
            @PathVariable Integer userId) {
        try {
            boolean hasReviewed = reviewService.hasUserReviewedProduct(productId, userId);
            return ResponseEntity.ok(new HasReviewedResponse(hasReviewed));
        } catch (Exception ex) {
            log.error("Error checking user review: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to check review status");
        }
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(
            @PathVariable Integer reviewId,
            @RequestBody Review review,
            @RequestHeader(value = "userId", required = false) Integer userId) {
        try {
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("User must be logged in to update a review");
            }

            log.info("Updating review {} by user {}", reviewId, userId);
            Review updatedReview = reviewService.updateReview(reviewId, review, userId);
            return ResponseEntity.ok(updatedReview);
        } catch (RuntimeException ex) {
            log.error("Failed to update review: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            log.error("Error updating review: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the review");
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(
            @PathVariable Integer reviewId,
            @RequestHeader(value = "userId", required = false) Integer userId) {
        try {
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("User must be logged in to delete a review");
            }

            log.info("Deleting review {} by user {}", reviewId, userId);
            reviewService.deleteReview(reviewId, userId);
            return ResponseEntity.ok("Review deleted successfully");
        } catch (RuntimeException ex) {
            log.error("Failed to delete review: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            log.error("Error deleting review: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the review");
        }
    }

    @PostMapping("/{reviewId}/helpful")
    public ResponseEntity<?> markReviewHelpful(
            @PathVariable Integer reviewId,
            @RequestParam boolean helpful) {
        try {
            log.info("Marking review {} as {}", reviewId, helpful ? "helpful" : "not helpful");
            reviewService.markReviewHelpful(reviewId, helpful);
            return ResponseEntity.ok("Review marked successfully");
        } catch (Exception ex) {
            log.error("Error marking review: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to mark review");
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingReviews(
            @RequestHeader(value = "userRole", required = false) String userRole) {
        try {
            if (!"ADMIN".equals(userRole)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Admin access required");
            }

            log.info("Getting pending reviews");
            List<Review> reviews = reviewService.getPendingReviews();
            return ResponseEntity.ok(reviews);
        } catch (Exception ex) {
            log.error("Error getting pending reviews: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve pending reviews");
        }
    }

    // Admin: Approve a review
    @PostMapping("/{reviewId}/approve")
    public ResponseEntity<?> approveReview(
            @PathVariable Integer reviewId,
            @RequestHeader(value = "userRole", required = false) String userRole) {
        try {
            if (!"ADMIN".equals(userRole)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Admin access required");
            }

            log.info("Approving review {}", reviewId);
            reviewService.approveReview(reviewId);
            return ResponseEntity.ok("Review approved successfully");
        } catch (Exception ex) {
            log.error("Error approving review: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to approve review");
        }
    }

    // Helper class for response
    private static class HasReviewedResponse {
        public boolean hasReviewed;
        public HasReviewedResponse(boolean hasReviewed) {
            this.hasReviewed = hasReviewed;
        }
    }
}