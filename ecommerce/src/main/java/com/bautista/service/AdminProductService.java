package com.bautista.service;

import com.bautista.dto.ProductDTO;
import com.bautista.entity.ProductData;
import com.bautista.repository.ProductDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminProductService {

    @Autowired
    private ProductDataRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Integer id) throws Exception {
        ProductData product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Product not found with id: " + id));
        return toDTO(product);
    }

    public ProductDTO createProduct(ProductDTO productDTO) throws Exception {
        // Check if SKU already exists
        if (productDTO.getSku() != null && !productDTO.getSku().isEmpty()) {
            log.info("Creating product with SKU: {}", productDTO.getSku());
        }

        ProductData productData = toEntity(productDTO);
        ProductData savedProduct = productRepository.save(productData);
        log.info("Product created successfully with id: {}", savedProduct.getId());

        return toDTO(savedProduct);
    }

    public ProductDTO updateProduct(Integer id, ProductDTO productDTO) throws Exception {
        ProductData existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Product not found with id: " + id));

        // Copy properties from dto to existing entity
        BeanUtils.copyProperties(productDTO, existingProduct, "id", "created");

        ProductData updatedProduct = productRepository.save(existingProduct);
        log.info("Product updated successfully with id: {}", updatedProduct.getId());

        return toDTO(updatedProduct);
    }

    public void deleteProduct(Integer id) throws Exception {
        ProductData product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Product not found with id: " + id));

        productRepository.delete(product);
        log.info("Product deleted successfully with id: {}", id);
    }

    public ProductDTO updateStock(Integer id, Integer newStock) throws Exception {
        ProductData product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Product not found with id: " + id));

        Integer oldStock = product.getStockQuantity();
        product.setStockQuantity(newStock);

        // Update status based on stock
        if (newStock == 0) {
            product.setStatus("Out of Stock");
        } else if (newStock <= 5) {
            product.setStatus("Low Stock");
        } else {
            product.setStatus("Active");
        }

        ProductData updatedProduct = productRepository.save(product);
        log.info("Product {} stock updated from {} to {}", id, oldStock, newStock);

        return toDTO(updatedProduct);
    }

    public List<ProductDTO> getLowStockProducts() {
        return productRepository.findByStockQuantityLessThanEqual(5).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getOutOfStockProducts() {
        return productRepository.findByStockQuantityLessThanEqual(0).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO toDTO(ProductData productData) {
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(productData, dto);
        return dto;
    }

    private ProductData toEntity(ProductDTO dto) {
        ProductData entity = new ProductData();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}