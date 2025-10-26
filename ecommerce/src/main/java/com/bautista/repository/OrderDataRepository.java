package com.bautista.repository;

import com.bautista.entity.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDataRepository extends JpaRepository<OrderData, Integer> {

    List<OrderData> findByUserId(Integer userId);

    List<OrderData> findByUserIdOrderByOrderDateDesc(Integer userId);

    Optional<OrderData> findByOrderNumber(String orderNumber);

    List<OrderData> findByStatus(String status);

    List<OrderData> findByUserIdAndStatus(Integer userId, String status);
}