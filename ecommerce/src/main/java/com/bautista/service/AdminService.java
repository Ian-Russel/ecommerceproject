package com.bautista.service;

import com.bautista.dto.DashboardStats;
import com.bautista.entity.OrderData;
import com.bautista.entity.ProductData;
import com.bautista.entity.UserData;
import com.bautista.repository.OrderDataRepository;
import com.bautista.repository.ProductDataRepository;
import com.bautista.repository.UserDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AdminService {

    @Autowired
    private OrderDataRepository orderRepository;

    @Autowired
    private ProductDataRepository productRepository;

    @Autowired
    private UserDataRepository userRepository;

    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();

        // Total orders
        stats.setTotalOrders(orderRepository.count());

        // Pending orders
        stats.setPendingOrders(orderRepository.countByStatus("Pending"));

        // Completed orders
        stats.setCompletedOrders(orderRepository.countByStatus("Completed"));

        // Total revenue (from completed orders)
        BigDecimal totalRevenue = orderRepository.sumTotalAmountByStatus("Completed");
        stats.setTotalRevenue(totalRevenue != null ? totalRevenue : BigDecimal.ZERO);

        // Average order value
        if (stats.getCompletedOrders() > 0) {
            stats.setAverageOrderValue(
                    stats.getTotalRevenue().divide(
                            BigDecimal.valueOf(stats.getCompletedOrders()),
                            2,
                            BigDecimal.ROUND_HALF_UP
                    )
            );
        } else {
            stats.setAverageOrderValue(BigDecimal.ZERO);
        }

        // Total products
        stats.setTotalProducts(productRepository.count());

        // Low stock products (stock <= 5)
        stats.setLowStockProducts(productRepository.countByStockQuantityLessThanEqual(5));

        // Out of stock products
        stats.setOutOfStockProducts(productRepository.countByStockQuantity(0));

        // Total customers
        stats.setTotalCustomers(userRepository.countByRole("CUSTOMER"));

        // New customers this month
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date startOfMonth = cal.getTime();

        stats.setNewCustomersThisMonth(
                userRepository.countByRoleAndCreatedAtAfter("CUSTOMER", startOfMonth)
        );

        log.info("Dashboard stats calculated: {} orders, {} revenue",
                stats.getTotalOrders(), stats.getTotalRevenue());

        return stats;
    }

    public List<UserData> getAllCustomers() {
        return userRepository.findByRole("CUSTOMER");
    }

    public List<UserData> getAllUsers() {
        return userRepository.findAll();
    }
}