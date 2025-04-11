package org.pchardwarestore.repository;

import org.pchardwarestore.entity.Category;
import org.pchardwarestore.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product addProduct(Product product);


    List<Product> findAllProducts();

    Optional<Product> findProductById(Long id);

    List<Product> findProductByName(String productName);

    List<Product> findProductByManufacturer(String manufacturer);

    List<Product> findProductByCategory(Category category);

    List<Product> findProductByPriceRange(double lower, double upper);

    List<Product> findOnlyInStock();

    Optional<Product> deleteProductById(Long id);
}
