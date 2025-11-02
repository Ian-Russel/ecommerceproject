package com.bautista.repository;

import com.bautista.entity.ReviewData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewDataRepository extends JpaRepository<ReviewData, Integer> {

    List<ReviewData> findByProductIdAndIsApprovedTrueOrderByCreatedAtDesc(Integer productId);

    List<ReviewData> findByUserIdOrderByCreatedAtDesc(Integer userId);

    List<ReviewData> findByProductIdAndIsApprovedOrderByCreatedAtDesc(Integer productId, Boolean isApproved);

    @Query("SELECT AVG(r.rating) FROM ReviewData r WHERE r.productId = :productId AND r.isApproved = true")
    Double calculateAverageRating(@Param("productId") Integer productId);

    Long countByProductIdAndIsApproved(Integer productId, Boolean isApproved);

    boolean existsByProductIdAndUserId(Integer productId, Integer userId);

    @Query("SELECT r.rating, COUNT(r) FROM ReviewData r WHERE r.productId = :productId AND r.isApproved = true GROUP BY r.rating ORDER BY r.rating DESC")
    List<Object[]> getRatingDistribution(@Param("productId") Integer productId);

    ReviewData findByProductIdAndUserId(Integer productId, Integer userId);

    List<ReviewData> findByIsApprovedFalseOrderByCreatedAtDesc();

    List<ReviewData> findAllByOrderByCreatedAtDesc();
}