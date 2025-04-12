package org.pchardwarestore.service;

import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.productEntity.Product;
import org.pchardwarestore.entity.productEntity.ProductStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {


    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public List<Product> findAllProducts() {
        return List.of();
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Product> findProductByName(String productName) {
        return List.of();
    }

    @Override
    public List<Product> findProductByManufacturer(String manufacturer) {
        return List.of();
    }

    @Override
    public List<Product> findProductByCategory(Category category) {
        return List.of();
    }

    @Override
    public List<Product> findProductByPriceRange(Double lower, Double upper) {
        return List.of();
    }

    @Override
    public List<Product> findOnlyInStock() {
        return List.of();
    }

    @Override
    public Optional<Product> findProductByStatus(ProductStatus status) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> updateProduct(Product product) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> deleteProductById(Long id) {
        return Optional.empty();
    }
}
