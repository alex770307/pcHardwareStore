package org.pchardwarestorefour.service.catalogService.productService;

import lombok.AllArgsConstructor;
import org.pchardwarestorefour.dto.catalogDto.productDto.ProductResponse;
import org.pchardwarestorefour.entity.catalogEntity.Category;
import org.pchardwarestorefour.entity.catalogEntity.Product;
import org.pchardwarestorefour.entity.catalogEntity.ProductStatus;
import org.pchardwarestorefour.repository.catalogRepository.ProductRepository;
import org.pchardwarestorefour.service.catalogService.categoryService.FindCategoryService;
import org.pchardwarestorefour.service.exception.NotFoundException;
import org.pchardwarestorefour.service.util.CatalogConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class FindProductService {
    private ProductRepository productRepository;
    private FindCategoryService findCategoryService;
    private CatalogConverter converter;

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(converter::fromProduct)
                .toList();
    }

    public ProductResponse findProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with ID '" + id + "' not found"));
        return converter.fromProduct(product);
    }

    public List<ProductResponse> findProductByName(String productName) {
        List<Product> products = productRepository.findByName(productName);
        if (products.isEmpty()) {
            throw new NotFoundException("No Products found for name '" + productName + "'");
        }
        return products.stream().map(converter::fromProduct).toList();
    }

    public List<ProductResponse> findByManufacturer(String manufacturer) {
        List<Product> products = productRepository.findByManufacturer(manufacturer);
        if (products.isEmpty()) {
            throw new NotFoundException("No Products found for manufacturer '" + manufacturer + "'");
        }
        return products.stream().map(converter::fromProduct).toList();
    }

    public List<ProductResponse> findByPriceBetween(Double lower, Double upper) {
        List<Product> products = productRepository.findByPriceBetween(lower, upper);
        if (products.isEmpty()) {
            throw new NotFoundException("Products in price range from '" + lower + "' to '" + upper + "' not found");
        }
        return products.stream().map(converter::fromProduct).toList();
    }

    public List<ProductResponse> findByQuantityGreaterThan(Integer quantity) {
        List<Product> products = productRepository.findByQuantityGreaterThan(quantity);
        if (products.isEmpty()) {
            throw new NotFoundException("No Products found for quantity greater than '" + quantity + "'");
        }
        return products.stream().map(converter::fromProduct).toList();
    }

    public List<ProductResponse> findByStatus(ProductStatus status) {
        List<Product> products = productRepository.findByStatus(status);
        if (products.isEmpty()) {
            throw new NotFoundException("No Products found for status '" + status + "'");
        }
        return products.stream().map(converter::fromProduct).toList();
    }

    public List<ProductResponse> findByCategoryName(String categoryName) {
        Category category = findCategoryService.findByNameOrThrow(categoryName);
        List<Product> products = productRepository.findByCategory(category);
        if (products.isEmpty()) {
            throw new NotFoundException("No Products found for category '" + categoryName + "'");
        }
        return products.stream().map(converter::fromProduct).toList();
    }

    public List<ProductResponse> findBySectionName(String sectionName) {
        if (StringUtils.hasText(sectionName)) {
            List<Product> products = productRepository.findAllByCategory_Section_Name(sectionName);
            if (products.isEmpty()) {
                throw new NotFoundException("No Products found for section '" + sectionName + "'");
            }
            return products.stream()
                    .map(converter::fromProduct).toList();
        } else {
            throw new NotFoundException("Section name '" + sectionName + "' is null or empty");
        }
    }

    public List<ProductResponse> searchProducts(String manufacturer, Double minPrice, Double maxPrice, ProductStatus status, String categoryName) {
        List<Product> products = productRepository.findAll();

        List<Product> filteredProducts = products.stream()
                .filter(p -> manufacturer == null || p.getManufacturer().equalsIgnoreCase(manufacturer))
                .filter(p -> minPrice == null || p.getPrice() >= minPrice)
                .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
                .filter(p -> status == null || p.getStatus() == status)
                .filter(p -> {
                    if (categoryName == null) return true;
                    Category category = p.getCategory();
                    return category != null && category.getName().equalsIgnoreCase(categoryName);
                })
                .toList();

        if (filteredProducts.isEmpty()) {
            throw new NotFoundException("No products found matching the given criteria");
        }

        return filteredProducts.stream()
                .map(converter::fromProduct)
                .toList();
    }
}
