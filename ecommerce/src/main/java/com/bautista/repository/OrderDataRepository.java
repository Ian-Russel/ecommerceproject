package com.bautista.repository;

import com.bautista.entity.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderDataRepository extends JpaRepository<OrderData, Integer> {

    List<OrderData> findByUserIdOrderByOrderDateDesc(Integer userId);

    Optional<OrderData> findByOrderNumber(String orderNumber);

    Long countByStatus(String status);

    @Query("SELECT SUM(o.totalAmount) FROM OrderData o WHERE o.status = :status")
    BigDecimal sumTotalAmountByStatus(@Param("status") String status);

    List<OrderData> findTop10ByOrderByOrderDateDesc();

    List<OrderData> findByUserId(Integer userId);

    List<OrderData> findByStatus(String status);

    List<OrderData> findByUserIdAndStatus(Integer userId, String status);
}