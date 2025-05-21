package org.pchardwarestore.service.util;

import org.pchardwarestore.dto.catalogDto.categoryDto.AddCategoryRequest;
import org.pchardwarestore.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestore.dto.catalogDto.productDto.AddProductRequest;
import org.pchardwarestore.dto.catalogDto.productDto.ProductResponse;
import org.pchardwarestore.dto.catalogDto.sectionDto.AddCategorySectionRequest;
import org.pchardwarestore.dto.catalogDto.sectionDto.CategorySectionResponse;
import org.pchardwarestore.entity.catalogEntity.*;
import org.pchardwarestore.service.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
                .photoLink(product.getPhotoLink())
                .photoLinks(product.getPhotos()
                        .stream()
                        .map(ProductPhoto::getLink)
                        .collect(Collectors.toList()))

                .category(fromCategory(product.getCategory()))
                .build();
    }

    public List<ProductResponse> fromProducts(List<Product> products) {
        return products.stream()
                .map(product -> fromProduct(product))
                .collect(Collectors.toList());
    }

    public ProductStatus toProductStatus(String statusString) {
        try {
            return ProductStatus.valueOf(statusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid product status: " + statusString);
        }
    }
}
