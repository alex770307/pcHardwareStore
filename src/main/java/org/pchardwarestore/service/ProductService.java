package org.pchardwarestore.service;

import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.productEntity.Product;
import org.pchardwarestore.entity.productEntity.ProductStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {



    public Product addProduct(Product product) {
        return null;
    }

    public List<Product> findAllProducts() {
        return List.of();
    }

    public Optional<Product> findProductById(Long id) {
        return Optional.empty();
    }

    public List<Product> findProductByName(String productName) {
        return List.of();
    }

    public List<Product> findProductByManufacturer(String manufacturer) {
        return List.of();
    }

    public List<Product> findProductByCategory(Category category) {
        return List.of();
    }

    public List<Product> findProductByPriceRange(Double lower, Double upper) {
        return List.of();
    }

    public List<Product> findOnlyInStock() {
        return List.of();
    }

    public Optional<Product> findProductByStatus(ProductStatus status) {
        return Optional.empty();
    }

    public Optional<Product> updateProduct(Product product) {
        return Optional.empty();
    }

    public Optional<Product> deleteProductById(Long id) {
        return Optional.empty();
    }
}
