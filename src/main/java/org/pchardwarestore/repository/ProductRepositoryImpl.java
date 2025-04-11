package org.pchardwarestore.repository;

import org.pchardwarestore.entity.Category;
import org.pchardwarestore.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private Map<Long, Product> productsDatabase;
    private Long productId;
    private Integer quantity;

    public ProductRepositoryImpl() {
        this.productsDatabase = new HashMap<>();
        this.productId = 0L;
        this.quantity = 0;
    }

    @Override
    public Product addProduct(Product product) {
        productId++;
        Product newProduct = new Product(productId,
                product.getName(),
                product.getDescription(),
                product.getManufacturer(),
                product.getPrice(),
                product.getQuantity(),
                product.getCreateDate(),
                product.getLastUpdateDate(),
                product.getStatus(),
                product.getCategory());
        productsDatabase.put(productId, newProduct);
        return newProduct;
    }

    @Override
    public List<Product> findAllProducts() {
        return new ArrayList<>(productsDatabase.values());
    }

    @Override
    public Optional<Product> findProductById(Long idFromProduct) {
        return Optional.ofNullable(productsDatabase.get(idFromProduct));
    }

    @Override
    public List<Product> findProductByName(String productName) {
        return productsDatabase.values().stream()
                .filter(product -> product.getName().equals(productName))
                .toList();
    }

    @Override
    public List<Product> findProductByManufacturer(String manufacturer) {
        return productsDatabase.values().stream()
                .filter(product -> product.getManufacturer().equals(manufacturer))
                .toList();
    }

    @Override
    public List<Product> findProductByCategory(Category category) {
        return productsDatabase.values().stream()
                .filter(product -> product.getCategory().equals(category))
                .toList();
    }

    @Override
    public List<Product> findProductByPriceRange(double lower, double upper) {
        return List.of();
    }

    @Override
    public List<Product> findOnlyInStock() {

        return productsDatabase.values().stream()
                .sorted()
                .toList();
    }

    @Override
    public Optional<Product> deleteProductById(Long idFromProduct) {
        return Optional.ofNullable(productsDatabase.remove(idFromProduct));
    }
}
