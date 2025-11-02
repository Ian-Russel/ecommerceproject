package com.bautista.controller;

import com.bautista.dto.MessageResponse;
import com.bautista.model.Review;
import com.bautista.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/admin/reviews")
@Slf4j
public class AdminReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllReviews() {
        try {
            log.info("Admin: Getting all reviews");
            List<Review> reviews = reviewService.getAllReviews();
            return ResponseEntity.ok(reviews);
        } catch (Exception ex) {
            log.error("Error getting all reviews: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to retrieve reviews"));
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingReviews() {
        try {
            log.info("Admin: Getting pending reviews");
            List<Review> reviews = reviewService.getPendingReviews();
            return ResponseEntity.ok(reviews);
        } catch (Exception ex) {
            log.error("Error getting pending reviews: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to retrieve pending reviews"));
        }
    }

    @PostMapping("/{reviewId}/approve")
    public ResponseEntity<MessageResponse> approveReview(@PathVariable Integer reviewId) {
        try {
            log.info("Admin: Approving review {}", reviewId);
            reviewService.approveReview(reviewId);
            return ResponseEntity.ok(new MessageResponse("Review approved successfully"));
        } catch (Exception ex) {
            log.error("Error approving review: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to approve review"));
        }
    }

    @PostMapping("/{reviewId}/reject")
    public ResponseEntity<MessageResponse> rejectReview(@PathVariable Integer reviewId) {
        try {
            log.info("Admin: Rejecting review {}", reviewId);
            reviewService.rejectReview(reviewId);
            return ResponseEntity.ok(new MessageResponse("Review rejected successfully"));
        } catch (Exception ex) {
            log.error("Error rejecting review: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to reject review"));
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<MessageResponse> deleteReview(@PathVariable Integer reviewId) {
        try {
            log.info("Admin: Deleting review {}", reviewId);
            reviewService.adminDeleteReview(reviewId);
            return ResponseEntity.ok(new MessageResponse("Review deleted successfully"));
        } catch (Exception ex) {
            log.error("Error deleting review: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to delete review"));
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductReviews(@PathVariable Integer productId) {
        try {
            log.info("Admin: Getting reviews for product {}", productId);
            List<Review> reviews = reviewService.getProductReviews(productId);
            return ResponseEntity.ok(reviews);
        } catch (Exception ex) {
            log.error("Error getting product reviews: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to retrieve product reviews"));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserReviews(@PathVariable Integer userId) {
        try {
            log.info("Admin: Getting reviews for user {}", userId);
            List<Review> reviews = reviewService.getUserReviews(userId);
            return ResponseEntity.ok(reviews);
        } catch (Exception ex) {
            log.error("Error getting user reviews: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to retrieve user reviews"));
        }
    }
}
