package com.bautista.repository;

import com.bautista.entity.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDataRepository extends JpaRepository<ProductData, Integer>,
        JpaSpecificationExecutor<ProductData> {

    @Query("SELECT DISTINCT p.categoryName FROM ProductData p WHERE p.categoryName IS NOT NULL")
    List<String> findDistinctCategories();

    @Query("SELECT DISTINCT p.brand FROM ProductData p WHERE p.brand IS NOT NULL")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT p.color FROM ProductData p WHERE p.color IS NOT NULL")
    List<String> findDistinctColors();

    @Query("SELECT DISTINCT p.gender FROM ProductData p WHERE p.gender IS NOT NULL")
    List<String> findDistinctGenders();

    Long countByStockQuantity(Integer stockQuantity);

    Long countByStockQuantityLessThanEqual(Integer stockQuantity);

    List<ProductData> findByStockQuantityLessThanEqual(Integer stockQuantity);
}