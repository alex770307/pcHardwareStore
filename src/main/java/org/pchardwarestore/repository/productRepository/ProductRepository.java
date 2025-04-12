package org.pchardwarestore.repository.productRepository;

import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.productEntity.Product;
import org.pchardwarestore.entity.productEntity.ProductStatus;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product addProduct(Product product);

    List<Product> findAllProducts();
    Optional<Product> findProductById(Long id);
    List<Product> findProductByName(String productName);
    List<Product> findProductByManufacturer(String manufacturer);
    List<Product> findProductByCategory(Category category);
    List<Product> findProductByPriceRange(Double lower, Double upper);
    List<Product> findOnlyInStock();
    List<Product> findProductByStatus(ProductStatus status);
//    Optional<Product> findProductByStatus(ProductStatus status);

    Optional<Product> updateProduct(Product product);

    Optional<Product> deleteProductById(Long id);
}
