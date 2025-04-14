package org.pchardwarestore.service.productService;

import org.pchardwarestore.dto.GeneralResponse;
import org.pchardwarestore.dto.categoryDto.CategoryResponseDto;
import org.pchardwarestore.dto.productDto.ProductResponseDto;
import org.pchardwarestore.entity.categoryEntity.Category;
import org.pchardwarestore.entity.categoryEntity.CategoryType;
import org.pchardwarestore.entity.productEntity.Product;
import org.pchardwarestore.entity.productEntity.ProductStatus;
import org.pchardwarestore.repository.productRepository.ProductRepository;
import org.pchardwarestore.service.categoryService.FindCategoryService;
import org.pchardwarestore.service.util.Converter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FindProductService {

    private ProductRepository repository;
    private FindCategoryService findCategoryService;
    private Converter converter;

    public FindProductService(ProductRepository repository, FindCategoryService findCategoryService, Converter converter) {
        this.repository = repository;
        this.findCategoryService = findCategoryService;
        this.converter = converter;
    }

    public List<ProductResponseDto> findAllProducts() {
        return repository.findAllProducts().stream()
                .map(product -> converter.toDto(product))
                .toList();
    }

    public List<Product> findAllDetails() {
        return repository.findAllProducts();
    }

    public GeneralResponse<ProductResponseDto> findProductById(Long id) {
        Optional<Product> foundedProductOptional = repository.findProductById(id);
        if (foundedProductOptional.isPresent()) {
            ProductResponseDto response = converter.toDto(foundedProductOptional.get());
            return new GeneralResponse<>(response);
        }
        GeneralResponse<ProductResponseDto> responseWithError = new GeneralResponse<>(null);
        responseWithError.addError("Product with id " + id + " not found");
        return responseWithError;
    }

    public GeneralResponse<List<ProductResponseDto>> findProductByName(String productName) {
        List<Product> foundedProducts = repository.findProductByName(productName);
        if (!foundedProducts.isEmpty()) {
            List<ProductResponseDto> response = foundedProducts.stream()
                    .map(product -> converter.toDto(product))
                    .toList();
            return new GeneralResponse<>(response);
        }
        GeneralResponse<List<ProductResponseDto>> responseWithError = new GeneralResponse<>(null);
        responseWithError.addError("Product with product name " + productName + " not found");
        return responseWithError;
    }

    public GeneralResponse<List<ProductResponseDto>> findProductByManufacturer(String manufacturer) {
        List<Product> foundedProducts = repository.findProductByManufacturer(manufacturer);
        if (!foundedProducts.isEmpty()) {
            List<ProductResponseDto> response = foundedProducts.stream()
                    .map(product -> converter.toDto(product))
                    .toList();
            return new GeneralResponse<>(response);
        }
        GeneralResponse<List<ProductResponseDto>> responseWithError = new GeneralResponse<>(null);
        responseWithError.addError("Product with product manufacturer " + manufacturer + " not found");
        return responseWithError;
    }

    public GeneralResponse<List<ProductResponseDto>> findProductByCategory(CategoryType categoryType) {
        GeneralResponse<CategoryResponseDto> categoryResponse = findCategoryService.findCategoryByType(categoryType);
        if (!categoryResponse.getErrors().isEmpty()) {
            GeneralResponse<List<ProductResponseDto>> responseWithError = new GeneralResponse<>(null);
            responseWithError.addError("Category with type '" + categoryType + "' not found");
            return responseWithError;
        }
        Category category = converter.categoryFromDto(categoryResponse.getBody());
        List<ProductResponseDto> productResponseDto = repository.findProductByCategory(category).stream()
                .map(product -> converter.toDto(product))
                .toList();
        GeneralResponse<List<ProductResponseDto>> responseWithProduct = new GeneralResponse<>(productResponseDto);
        return responseWithProduct;
    }

    public GeneralResponse<List<ProductResponseDto>> findProductByPriceRange(Double lower, Double upper) {
        List<Product> foundedProducts = repository.findProductByPriceRange(lower, upper);
        if (!foundedProducts.isEmpty()) {
            List<ProductResponseDto> response = foundedProducts.stream()
                    .map(product -> converter.toDto(product))
                    .toList();
            return new GeneralResponse<>(response);
        }
        GeneralResponse<List<ProductResponseDto>> responseWithError = new GeneralResponse<>(null);
        responseWithError.addError("Product in the price range from '" + lower + "' to '" + upper + "' not found");
        return responseWithError;
    }

    public GeneralResponse<List<ProductResponseDto>> findOnlyInStock(Integer quantity) {
        List<Product> foundedProducts = repository.findOnlyInStock(quantity);
        if (!foundedProducts.isEmpty()) {
            List<ProductResponseDto> response = foundedProducts.stream()
                    .map(product -> converter.toDto(product))
                    .toList();
            return new GeneralResponse<>(response);
        }
        GeneralResponse<List<ProductResponseDto>> responseWithError = new GeneralResponse<>(null);
        responseWithError.addError("Product is currently unavailable");
        return responseWithError;
    }

    public GeneralResponse<List<ProductResponseDto>> findProductByStatus(ProductStatus status) {
        List<Product> foundedProducts = repository.findProductByStatus(status);
        if (!foundedProducts.isEmpty()) {
            List<ProductResponseDto> response = foundedProducts.stream()
                    .map(converter::toDto)
                    .toList();
            return new GeneralResponse<>(response);
        }
        GeneralResponse<List<ProductResponseDto>> responseWithError = new GeneralResponse<>(null);
        responseWithError.addError("Products with status " + status + " not found");
        return responseWithError;
    }
}
