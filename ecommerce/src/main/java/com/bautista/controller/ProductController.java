package com.bautista.controller;

import com.bautista.dto.ProductSearchRequest;
import com.bautista.model.Product;
import com.bautista.model.ProductCategory;
import com.bautista.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/api/product")
    public ResponseEntity<?> getProductCategories() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            List<ProductCategory> mappedProducts = productService.listProductCategories();
            log.warn("Product Categories Count:::::::" + mappedProducts.size());
            response = ResponseEntity.ok(mappedProducts);
        }
        catch( Exception ex) {
            log.error("Failed to retrieve product with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    // NEW: Search and filter endpoint
    @GetMapping("/api/product/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false, defaultValue = "name_asc") String sortBy
    ) {
        try {
            ProductSearchRequest searchRequest = new ProductSearchRequest();
            searchRequest.setQuery(query);
            searchRequest.setCategory(category);
            searchRequest.setBrand(brand);
            searchRequest.setColor(color);
            searchRequest.setGender(gender);
            searchRequest.setMinPrice(minPrice);
            searchRequest.setMaxPrice(maxPrice);
            searchRequest.setSortBy(sortBy);

            log.info("Search request: {}", searchRequest);
            List<Product> products = productService.searchProducts(searchRequest);
            return ResponseEntity.ok(products);
        }
        catch (Exception ex) {
            log.error("Search failed: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    // NEW: Get all unique filter values
    @GetMapping("/api/product/filters")
    public ResponseEntity<?> getFilterOptions() {
        try {
            return ResponseEntity.ok(productService.getFilterOptions());
        }
        catch (Exception ex) {
            log.error("Failed to get filter options: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/api/product")
    public ResponseEntity<?> add(@RequestBody Product product) {
        log.info("Input >> " + product.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Product newProduct = productService.create(product);
            log.info("created product >> " + newProduct.toString());
            response = ResponseEntity.ok(newProduct);
        }
        catch( Exception ex) {
            log.error("Failed to retrieve product with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping("/api/product")
    public ResponseEntity<?> update(@RequestBody Product product) {
        log.info("Update Input >> product.toString() ");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Product newProduct = productService.update(product);
            response = ResponseEntity.ok(product);
        }
        catch( Exception ex) {
            log.error("Failed to retrieve product with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @GetMapping("api/product/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id) {
        log.info("Input product id >> " + Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Product product = productService.get(id);
            response = ResponseEntity.ok(product);
        }
        catch( Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @DeleteMapping("/api/product/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        log.info("Input >> " + Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            productService.delete(id);
            response = ResponseEntity.ok(null);
        }
        catch( Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
}