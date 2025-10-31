package com.bautista.controller;

import com.bautista.dto.ProductDTO;
import com.bautista.service.AdminProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AdminProductController {

    @Autowired
    private AdminProductService adminProductService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            List<ProductDTO> products = adminProductService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception ex) {
            log.error("Failed to get products: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Integer id) {
        try {
            ProductDTO product = adminProductService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception ex) {
            log.error("Failed to get product: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            log.info("Creating new product: {}", productDTO.getName());
            ProductDTO created = adminProductService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception ex) {
            log.error("Failed to create product: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Integer id,
            @RequestBody ProductDTO productDTO
    ) {
        try {
            log.info("Updating product with id: {}", id);
            ProductDTO updated = adminProductService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            log.error("Failed to update product: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        try {
            log.info("Deleting product with id: {}", id);
            adminProductService.deleteProduct(id);
            return ResponseEntity.ok(new SuccessResponse("Product deleted successfully"));
        } catch (Exception ex) {
            log.error("Failed to delete product: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> updateStock(
            @PathVariable Integer id,
            @RequestParam Integer stock
    ) {
        try {
            log.info("Updating stock for product {}: new stock = {}", id, stock);
            ProductDTO updated = adminProductService.updateStock(id, stock);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            log.error("Failed to update stock: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/low-stock")
    public ResponseEntity<?> getLowStockProducts() {
        try {
            List<ProductDTO> products = adminProductService.getLowStockProducts();
            return ResponseEntity.ok(products);
        } catch (Exception ex) {
            log.error("Failed to get low stock products: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/out-of-stock")
    public ResponseEntity<?> getOutOfStockProducts() {
        try {
            List<ProductDTO> products = adminProductService.getOutOfStockProducts();
            return ResponseEntity.ok(products);
        } catch (Exception ex) {
            log.error("Failed to get out of stock products: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @lombok.Data
    @lombok.AllArgsConstructor
    static class ErrorResponse {
        private String message;
    }

    @lombok.Data
    @lombok.AllArgsConstructor
    static class SuccessResponse {
        private String message;
    }
}