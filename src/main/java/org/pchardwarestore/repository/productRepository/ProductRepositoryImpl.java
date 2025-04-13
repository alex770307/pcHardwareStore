package org.pchardwarestore.repository.productRepository;

import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.productEntity.Product;
import org.pchardwarestore.entity.productEntity.ProductStatus;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private Map<Long, Product> productsDatabase;
    private Long productId;

    public ProductRepositoryImpl() {
        this.productsDatabase = new HashMap<>();
        this.productId = 0L;
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
    public List<Product> findProductByPriceRange(Double lower, Double upper) {
        List<Product> productsByPriceRange = new ArrayList<>();
        productsDatabase.values().stream()
                .filter(product -> product.getPrice() >= lower && product.getPrice() <= upper)
                .forEach(product -> productsByPriceRange.add(product));
        return productsByPriceRange;

    }

    @Override
    public List<Product> findOnlyInStock(Integer quantity) {
        List<Product> productsOnlyInStock = new ArrayList<>();
        productsDatabase.values().stream()
                .filter(product -> product.getQuantity() > 0)
                .forEach(product -> productsOnlyInStock.add(product));
        return productsOnlyInStock;
    }

//    @Override
//    public Optional<Product> findProductByStatus(ProductStatus status) {
//        return Optional.ofNullable(productsDatabase.get(status));
//    }

    @Override
    public List<Product> findProductByStatus(ProductStatus status) {
        List<Product> productsByStatus = new ArrayList<>();
        productsDatabase.values().stream()
                .filter(product -> product.getStatus() == status)
                .forEach(product -> productsByStatus.add(product));
        return productsByStatus;
    }

    @Override
    public Optional<Product> updateProduct(Product product) {
        Long productId = product.getIdForProduct();
        Optional<Product> updatedProduct = findProductById(productId);
        if (updatedProduct.isPresent()) {
            updatedProduct.get().setName(product.getName());
            updatedProduct.get().setDescription(product.getDescription());
            updatedProduct.get().setManufacturer(product.getManufacturer());
            updatedProduct.get().setPrice(product.getPrice());
            updatedProduct.get().setQuantity(product.getQuantity());
            updatedProduct.get().setCreateDate(product.getCreateDate());
            updatedProduct.get().setLastUpdateDate(product.getLastUpdateDate());
            updatedProduct.get().setStatus(product.getStatus());
            updatedProduct.get().setCategory(product.getCategory());
            productsDatabase.put(productId, updatedProduct.get());
            return Optional.of(updatedProduct.get());

        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> deleteProductById(Long idFromProduct) {
        return Optional.ofNullable(productsDatabase.remove(idFromProduct));
    }
}
