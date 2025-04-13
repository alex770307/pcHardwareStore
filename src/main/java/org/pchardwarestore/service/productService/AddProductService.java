package org.pchardwarestore.service.productService;

import org.pchardwarestore.dto.GeneralResponse;
import org.pchardwarestore.dto.categoryDto.CategoryResponseDto;
import org.pchardwarestore.dto.productDto.ProductRequestDto;
import org.pchardwarestore.dto.productDto.ProductResponseDto;
import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.productEntity.Product;
import org.pchardwarestore.entity.productEntity.ProductStatus;
import org.pchardwarestore.repository.productRepository.ProductRepository;
import org.pchardwarestore.service.categoryService.AddCategoryService;
import org.pchardwarestore.service.categoryService.FindCategoryService;
import org.pchardwarestore.service.util.Converter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AddProductService {
    private ProductRepository repository;
    private AddCategoryService addCategoryService;
    private FindCategoryService findCategoryService;
    private Converter converter;

    public AddProductService(ProductRepository repository, AddCategoryService addCategoryService,
                             FindCategoryService findCategoryService, Converter converter) {
        this.repository = repository;
        this.addCategoryService = addCategoryService;
        this.findCategoryService = findCategoryService;
        this.converter = converter;
    }

    public GeneralResponse<ProductResponseDto> createNewProduct(ProductRequestDto request) {
        GeneralResponse<CategoryResponseDto> categoryResponse = findCategoryService.findCategoryById(request.getCategoryID());
        if (!categoryResponse.getErrors().isEmpty()) {
            GeneralResponse<ProductResponseDto> responseWithError = new GeneralResponse<>(null);
            responseWithError.addError("Category with id '" + request.getCategoryID() + "' not found");
            return responseWithError;
        }
        Product newProduct = converter.fromDto(request);
        newProduct.setCreateDate(LocalDateTime.now());
        newProduct.setLastUpdateDate(LocalDateTime.now());
        newProduct.setStatus(ProductStatus.AVAILABLE);

        Category category = converter.categoryFromDto(categoryResponse.getBody());
        newProduct.setCategory(category);

        Product savedProduct = repository.addProduct(newProduct);
        ProductResponseDto response = converter.toDto(savedProduct);
        return new GeneralResponse<>(response);
    }
}
