package com.bautista.service;

import com.bautista.dto.CheckoutRequest;
import com.bautista.entity.OrderData;
import com.bautista.entity.OrderItemData;
import com.bautista.entity.ProductData;
import com.bautista.enums.OrderItemStatus;
import com.bautista.model.Order;
import com.bautista.model.OrderItem;
import com.bautista.repository.OrderDataRepository;
import com.bautista.repository.OrderItemDataRepository;
import com.bautista.repository.ProductDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderDataRepository orderRepository;

    @Autowired
    private OrderItemDataRepository orderItemRepository;

    @Autowired
    private ProductDataRepository productRepository;

    @Transactional
    public Order createOrder(CheckoutRequest request) throws Exception {
        for (var orderItemReq : request.getOrderItems()) {
            ProductData product = productRepository.findById(orderItemReq.getProductId())
                    .orElseThrow(() -> new Exception("Product not found: " + orderItemReq.getProductId()));

            if (product.getStockQuantity() < orderItemReq.getQuantity()) {
                throw new Exception("Insufficient stock for product: " + product.getName()
                        + ". Available: " + product.getStockQuantity()
                        + ", Requested: " + orderItemReq.getQuantity());
            }
        }

        OrderData orderData = new OrderData();
        BeanUtils.copyProperties(request, orderData);

        List<OrderItemData> orderItems = request.getOrderItems().stream()
                .map(item -> {
                    OrderItemData itemData = new OrderItemData();
                    BeanUtils.copyProperties(item, itemData);

                    itemData.setId(null);
                    itemData.setOrder(orderData);
                    itemData.setCustomerId(request.getUserId());
                    itemData.setStatus(OrderItemStatus.PENDING);
                    return itemData;
                })
                .collect(Collectors.toList());

        orderData.setOrderItems(orderItems);

        OrderData savedOrder = orderRepository.save(orderData);

        updateProductStock(savedOrder);

        log.info("Order created successfully: {}", savedOrder.getOrderNumber());
        return toOrder(savedOrder);
    }

    private void updateProductStock(OrderData orderData) throws Exception {
        for (OrderItemData item : orderData.getOrderItems()) {
            ProductData product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new Exception("Product not found: " + item.getProductId()));

            int previousStock = product.getStockQuantity();
            int newStock = previousStock - item.getQuantity();

            product.setStockQuantity(newStock);
            productRepository.save(product);

            log.info("Updated stock for product {} ({}): {} -> {}",
                    product.getId(), product.getName(), previousStock, newStock);
        }
    }

    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserIdOrderByOrderDateDesc(userId).stream()
                .map(this::toOrder)
                .collect(Collectors.toList());
    }

    public Order getOrderById(Integer orderId) throws Exception {
        OrderData orderData = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found"));
        return toOrder(orderData);
    }

    public Order getOrderByNumber(String orderNumber) throws Exception {
        OrderData orderData = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new Exception("Order not found"));
        return toOrder(orderData);
    }

    @Transactional
    public Order updateOrderStatus(Integer orderId, String status) throws Exception {
        OrderData orderData = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found"));

        orderData.setStatus(status);
        OrderData updated = orderRepository.save(orderData);

        log.info("Order {} status updated to: {}", orderData.getOrderNumber(), status);
        return toOrder(updated);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toOrder)
                .collect(Collectors.toList());
    }

    private Order toOrder(OrderData orderData) {
        Order order = new Order();
        BeanUtils.copyProperties(orderData, order);

        if (orderData.getOrderItems() != null) {
            List<OrderItem> items = orderData.getOrderItems().stream()
                    .map(itemData -> {
                        OrderItem item = new OrderItem();
                        BeanUtils.copyProperties(itemData, item);
                        return item;
                    })
                    .collect(Collectors.toList());
            order.setOrderItems(items);
        }

        return order;
    }
}