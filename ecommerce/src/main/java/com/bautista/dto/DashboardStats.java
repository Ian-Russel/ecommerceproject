package com.bautista.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DashboardStats {
    private Long totalOrders;
    private Long pendingOrders;
    private Long completedOrders;
    private BigDecimal totalRevenue;
    private Long totalProducts;
    private Long lowStockProducts; // When products with stock <= 5
    private Long totalCustomers;
    private Long newCustomersThisMonth;
    private BigDecimal averageOrderValue;
    private Long outOfStockProducts;
}