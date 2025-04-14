package org.pchardwarestore.service.util;

import org.pchardwarestore.dto.categoryDto.CategoryRequestDto;
import org.pchardwarestore.dto.categoryDto.CategoryResponseDto;
import org.pchardwarestore.dto.productDto.ProductRequestDto;
import org.pchardwarestore.dto.productDto.ProductResponseDto;
import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.productEntity.Product;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public Product fromDto(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setManufacturer(dto.getManufacturer());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
//        product.setStatus(dto.getStatus());

        return product;
    }

    public ProductResponseDto toDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setIdForProduct(product.getIdForProduct());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setManufacturer(product.getManufacturer());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setStatus(product.getStatus());

        Category categoryFromProduct = product.getCategory();
        CategoryResponseDto categoryResponseDto = dtoFromCategory(categoryFromProduct);

        dto.setCategoryDto(categoryResponseDto);

        return dto;
    }

    public CategoryResponseDto dtoFromCategory(Category category) {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(
                category.getIdForCategory(),
                category.getName(),
                category.getDescription(),
                category.getCategoryType()
        );
        return categoryResponseDto;
    }

    public Category categoryFromDto(CategoryRequestDto dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setCategoryType(dto.getCategoryType());
        return category;
    }

    public Category categoryFromDto(CategoryResponseDto body) {
        Category category = new Category();
        category.setName(body.getName());
        category.setDescription(body.getDescription());
        category.setCategoryType(body.getCategoryType());
        return category;
    }

}
