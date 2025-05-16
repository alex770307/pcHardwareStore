package org.pchardwarestorefour.service.util;

import org.pchardwarestorefour.dto.catalogDto.categoryDto.AddCategoryRequest;
import org.pchardwarestorefour.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestorefour.dto.catalogDto.productDto.AddProductRequest;
import org.pchardwarestorefour.dto.catalogDto.productDto.ProductResponse;
import org.pchardwarestorefour.dto.catalogDto.sectionDto.AddCategorySectionRequest;
import org.pchardwarestorefour.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestorefour.entity.catalogEntity.Category;
import org.pchardwarestorefour.entity.catalogEntity.CategorySection;
import org.pchardwarestorefour.entity.catalogEntity.Product;
import org.pchardwarestorefour.entity.catalogEntity.ProductStatus;
import org.pchardwarestorefour.service.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CatalogConverter {

    // ---------- CategorySection ----------
    public CategorySection toSection(AddCategorySectionRequest request) {
        return CategorySection.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public CategorySectionResponse fromSection(CategorySection section) {
        return CategorySectionResponse.builder()
                .id(section.getId())
                .name(section.getName())
                .description(section.getDescription())
                .build();
    }

    // ---------- Category ----------
    public Category toCategory(AddCategoryRequest request, CategorySection section) {
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .section(section)
                .build();
    }

    public CategoryResponse fromCategory(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .section(fromSection(category.getSection()))
                .build();
    }

    // ---------- Product ----------
    public Product toProduct(AddProductRequest request, Category category) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .manufacturer(request.getManufacturer())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .status(ProductStatus.AVAILABLE)
                .category(category)
                .build();
    }

    public ProductResponse fromProduct(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .manufacturer(product.getManufacturer())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .createDate(product.getCreateDate())
                .lastUpdateDate(product.getLastUpdateDate())
                .status(product.getStatus())
                .category(fromCategory(product.getCategory()))
                .build();
    }

    public ProductStatus toProductStatus(String statusString) {
        try {
            return ProductStatus.valueOf(statusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid product status: " + statusString);
        }
    }
}
