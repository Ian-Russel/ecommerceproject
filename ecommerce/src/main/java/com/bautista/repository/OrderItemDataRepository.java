package com.bautista.repository;

import com.bautista.entity.OrderItemData;
import com.bautista.enums.OrderItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemDataRepository extends JpaRepository<OrderItemData, Integer> {

    List<OrderItemData> findByOrderId(Integer orderId);

    List<OrderItemData> findAllByCustomerId(Integer customerId);

    List<OrderItemData> findByCustomerIdAndStatus(Integer customerId, OrderItemStatus status);
}