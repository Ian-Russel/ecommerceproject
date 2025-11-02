package com.bautista.serviceimpl;

import com.bautista.entity.OrderData;
import com.bautista.entity.ProductData;
import com.bautista.entity.ReviewData;
import com.bautista.entity.UserData;
import com.bautista.model.Review;
import com.bautista.model.ReviewRequest;
import com.bautista.model.ReviewSummary;
import com.bautista.repository.OrderDataRepository;
import com.bautista.repository.ProductDataRepository;
import com.bautista.repository.ReviewDataRepository;
import com.bautista.repository.UserDataRepository;
import com.bautista.service.ReviewService;
import com.bautista.util.Transform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDataRepository reviewRepository;

    @Autowired
    private ProductDataRepository productRepository;

    @Autowired
    private UserDataRepository userRepository;

    @Autowired
    private OrderDataRepository orderRepository;

    @Autowired
    private Transform transform;

    @Override
    @Transactional
    public Review createReview(ReviewRequest request, Integer userId) {
        UserData user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductData product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (reviewRepository.existsByProductIdAndUserId(request.getProductId(), userId)) {
            throw new RuntimeException("You have already reviewed this product");
        }

        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        ReviewData reviewData = new ReviewData();
        reviewData.setProductId(request.getProductId());
        reviewData.setUserId(userId);
        reviewData.setCustomerName(user.getFirstName() + " " + user.getLastName());
        reviewData.setCustomerEmail(user.getEmail());
        reviewData.setRating(request.getRating());
        reviewData.setTitle(request.getTitle());
        reviewData.setComment(request.getComment());

        OrderData order = findUserOrderForProduct(userId, request.getProductId());
        if (order != null) {
            reviewData.setVerifiedPurchase(true);
            reviewData.setOrderId(order.getId());
        } else {
            reviewData.setVerifiedPurchase(false);
        }

        reviewData.setIsApproved(true);

        ReviewData savedReview = reviewRepository.save(reviewData);

        updateProductRating(request.getProductId());

        log.info("Review created successfully: {} for product {}", savedReview.getId(), request.getProductId());
        return transform.toReview(savedReview);
    }

    @Override
    @Transactional
    public Review updateReview(Integer reviewId, Review review, Integer userId) {
        ReviewData existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!existingReview.getUserId().equals(userId)) {
            throw new RuntimeException("You can only update your own reviews");
        }

        if (review.getRating() != null && review.getRating() >= 1 && review.getRating() <= 5) {
            existingReview.setRating(review.getRating());
        }
        if (review.getTitle() != null) {
            existingReview.setTitle(review.getTitle());
        }
        if (review.getComment() != null) {
            existingReview.setComment(review.getComment());
        }

        ReviewData updatedReview = reviewRepository.save(existingReview);

        updateProductRating(existingReview.getProductId());

        log.info("Review updated successfully: {}", reviewId);
        return transform.toReview(updatedReview);
    }

    @Override
    @Transactional
    public void deleteReview(Integer reviewId, Integer userId) {
        ReviewData review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUserId().equals(userId)) {
            throw new RuntimeException("You can only delete your own reviews");
        }

        Integer productId = review.getProductId();
        reviewRepository.delete(review);

        updateProductRating(productId);

        log.info("Review deleted successfully: {}", reviewId);
    }

    @Override
    public Review getReview(Integer reviewId) {
        ReviewData reviewData = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return transform.toReview(reviewData);
    }

    @Override
    public List<Review> getProductReviews(Integer productId) {
        List<ReviewData> reviews = reviewRepository
                .findByProductIdAndIsApprovedTrueOrderByCreatedAtDesc(productId);
        return reviews.stream()
                .map(transform::toReview)
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> getUserReviews(Integer userId) {
        List<ReviewData> reviews = reviewRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return reviews.stream()
                .map(transform::toReview)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewSummary getProductReviewSummary(Integer productId) {
        ReviewSummary summary = new ReviewSummary();
        summary.setProductId(productId);

        Double avgRating = reviewRepository.calculateAverageRating(productId);
        summary.setAverageRating(avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0);

        Long totalReviews = reviewRepository.countByProductIdAndIsApproved(productId, true);
        summary.setTotalReviews(totalReviews);

        List<Object[]> distribution = reviewRepository.getRatingDistribution(productId);
        summary.setFiveStarCount(0L);
        summary.setFourStarCount(0L);
        summary.setThreeStarCount(0L);
        summary.setTwoStarCount(0L);
        summary.setOneStarCount(0L);

        for (Object[] row : distribution) {
            Integer rating = (Integer) row[0];
            Long count = (Long) row[1];

            switch (rating) {
                case 5: summary.setFiveStarCount(count); break;
                case 4: summary.setFourStarCount(count); break;
                case 3: summary.setThreeStarCount(count); break;
                case 2: summary.setTwoStarCount(count); break;
                case 1: summary.setOneStarCount(count); break;
            }
        }

        return summary;
    }

    @Override
    public boolean hasUserReviewedProduct(Integer productId, Integer userId) {
        return reviewRepository.existsByProductIdAndUserId(productId, userId);
    }

    @Override
    @Transactional
    public void markReviewHelpful(Integer reviewId, boolean helpful) {
        ReviewData review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (helpful) {
            review.setHelpfulCount(review.getHelpfulCount() + 1);
        } else {
            review.setNotHelpfulCount(review.getNotHelpfulCount() + 1);
        }

        reviewRepository.save(review);

        log.info("Review {} marked as {}. Helpful: {}, Not Helpful: {}",
                reviewId,
                helpful ? "helpful" : "not helpful",
                review.getHelpfulCount(),
                review.getNotHelpfulCount());
    }

    @Override
    @Transactional
    public void approveReview(Integer reviewId) {
        ReviewData review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setIsApproved(true);
        reviewRepository.save(review);

        updateProductRating(review.getProductId());

        log.info("Review approved: {}", reviewId);
    }

    @Override
    public List<Review> getPendingReviews() {
        List<ReviewData> reviews = reviewRepository.findByIsApprovedFalseOrderByCreatedAtDesc();
        return reviews.stream()
                .map(transform::toReview)
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> getAllReviews() {
        List<ReviewData> reviews = reviewRepository.findAllByOrderByCreatedAtDesc();
        return reviews.stream()
                .map(transform::toReview)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void rejectReview(Integer reviewId) {
        ReviewData review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setIsApproved(false);
        reviewRepository.save(review);

        log.info("Review rejected: {}", reviewId);
    }

    @Override
    @Transactional
    public void adminDeleteReview(Integer reviewId) {
        ReviewData review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        Integer productId = review.getProductId();
        reviewRepository.delete(review);

        updateProductRating(productId);

        log.info("Admin deleted review: {}", reviewId);
    }

    private OrderData findUserOrderForProduct(Integer userId, Integer productId) {
        try {
            List<OrderData> orders = orderRepository.findByUserId(userId);

            for (OrderData order : orders) {
                if (order.getOrderItems() != null) {
                    boolean hasProduct = order.getOrderItems().stream()
                            .anyMatch(item -> item.getProductId().equals(productId));
                    if (hasProduct) {
                        return order;
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Could not verify purchase for user {} and product {}: {}",
                    userId, productId, e.getMessage());
        }
        return null;
    }

    private void updateProductRating(Integer productId) {
        try {
            ProductData product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                Double avgRating = reviewRepository.calculateAverageRating(productId);
                Long reviewCount = reviewRepository.countByProductIdAndIsApproved(productId, true);

                product.setRating(avgRating != null ? avgRating : 0.0);
                product.setReviewCount(reviewCount != null ? reviewCount.intValue() : 0);

                productRepository.save(product);
                log.info("Updated product {} rating to {} with {} reviews",
                        productId, product.getRating(), product.getReviewCount());
            }
        } catch (Exception e) {
            log.error("Failed to update product rating: {}", e.getMessage(), e);
        }
    }
}