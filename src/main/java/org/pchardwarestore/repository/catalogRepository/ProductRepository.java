package org.pchardwarestore.repository.catalogRepository;


import org.pchardwarestore.entity.catalogEntity.Category;
import org.pchardwarestore.entity.catalogEntity.Product;
import org.pchardwarestore.entity.catalogEntity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name);
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByManufacturer(String manufacturer);
    List<Product> findByPriceBetween(Double lower, Double upper);
    List<Product> findByQuantityGreaterThan(Integer quantity);
    List<Product> findByStatus(ProductStatus status);
    List<Product> findByCategory(Category category);
    List<Product> findAllByCategory_Section_Name(String sectionName);
    boolean existsByName(String name);

}
