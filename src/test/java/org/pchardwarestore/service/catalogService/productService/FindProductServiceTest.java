package org.pchardwarestore.service.catalogService.productService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.dto.catalogDto.productDto.ProductResponse;
import org.pchardwarestore.entity.catalogEntity.Category;
import org.pchardwarestore.entity.catalogEntity.Product;
import org.pchardwarestore.entity.catalogEntity.ProductStatus;
import org.pchardwarestore.repository.catalogRepository.ProductRepository;
import org.pchardwarestore.service.catalogService.categoryService.FindCategoryService;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.CatalogConverter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class FindProductServiceTest {
    private ProductRepository productRepository;
    private FindCategoryService findCategoryService;
    private CatalogConverter converter;
    private FindProductService findProductService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        findCategoryService = mock(FindCategoryService.class);
        converter = mock(CatalogConverter.class);
        findProductService = new FindProductService(productRepository, findCategoryService, converter);
    }

    private Product createSampleProduct() {
        return new Product(1L, "GTX 4090", "GPU", "Nvidia", 2000.0, 10,
                LocalDateTime.now(), LocalDateTime.now(), ProductStatus.AVAILABLE, new Category());
    }

    private ProductResponse createSampleResponse() {
        return new ProductResponse(1L, "GTX 4090", "GPU", "Nvidia", 2000.0, 10,
                LocalDateTime.now(), LocalDateTime.now(), ProductStatus.AVAILABLE, null);
    }

    @Test
    void findAll_shouldReturnAllProducts() {
        Product product = createSampleProduct();
        ProductResponse response = createSampleResponse();

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(converter.fromProduct(product)).thenReturn(response);

        List<ProductResponse> result = findProductService.findAll();

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }

    @Test
    void findProductById_shouldReturnProductResponse_whenProductExists() {
        Long productId = 1L;
        Product product = createSampleProduct();
        ProductResponse expectedResponse = createSampleResponse();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(converter.fromProduct(product)).thenReturn(expectedResponse);

        ProductResponse actualResponse = findProductService.findProductById(productId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void findProductById_shouldThrowNotFoundException_whenProductNotFound() {
        Long productId = 2L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> findProductService.findProductById(productId));
    }

    @Test
    void findProductByName_shouldReturnProductResponses_whenProductsFound() {
        String productName = "GTX 4090";
        Product product = createSampleProduct();
        ProductResponse productResponse = createSampleResponse();

        when(productRepository.findByName(productName)).thenReturn(List.of(product));
        when(converter.fromProduct(product)).thenReturn(productResponse);

        List<ProductResponse> actualResponses = findProductService.findProductByName(productName);

        assertEquals(1, actualResponses.size());
        assertEquals(productResponse, actualResponses.get(0));
    }

    @Test
    void findProductByName_shouldThrowNotFoundException_whenNoProductsFound() {
        String productName = "Nonexistent Product";
        when(productRepository.findByName(productName)).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> findProductService.findProductByName(productName));
    }

    @Test
    void findByCategoryName_shouldReturnProductResponses_whenCategoryFound() {
        String categoryName = "Graphics Cards";
        Category category = new Category();
        Product product = createSampleProduct();
        ProductResponse productResponse = createSampleResponse();

        when(findCategoryService.findByNameOrThrow(categoryName)).thenReturn(category);
        when(productRepository.findByCategory(category)).thenReturn(List.of(product));
        when(converter.fromProduct(product)).thenReturn(productResponse);

        List<ProductResponse> actualResponses = findProductService.findByCategoryName(categoryName);

        assertEquals(1, actualResponses.size());
        assertEquals(productResponse, actualResponses.get(0));
    }

    @Test
    void findByCategoryName_shouldThrowNotFoundException_whenCategoryNotFound() {
        String categoryName = "Nonexistent Category";
        when(findCategoryService.findByNameOrThrow(categoryName)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> findProductService.findByCategoryName(categoryName));
    }

    @Test
    void findByManufacturer_shouldReturnProducts_whenFound() {
        String manufacturer = "Nvidia";
        Product product = createSampleProduct();
        ProductResponse response = createSampleResponse();

        when(productRepository.findByManufacturer(manufacturer)).thenReturn(List.of(product));
        when(converter.fromProduct(product)).thenReturn(response);

        List<ProductResponse> result = findProductService.findByManufacturer(manufacturer);

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }

    @Test
    void findByManufacturer_shouldThrow_whenEmpty() {
        when(productRepository.findByManufacturer("AMD")).thenReturn(List.of());

        assertThrows(NotFoundException.class,
                () -> findProductService.findByManufacturer("AMD"));
    }

    @Test
    void findByPriceBetween_shouldReturnProducts_whenFound() {
        Product product = createSampleProduct();
        ProductResponse response = createSampleResponse();

        when(productRepository.findByPriceBetween(1000.0, 2500.0)).thenReturn(List.of(product));
        when(converter.fromProduct(product)).thenReturn(response);

        List<ProductResponse> result = findProductService.findByPriceBetween(1000.0, 2500.0);

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }

    @Test
    void findByPriceBetween_shouldThrow_whenEmpty() {
        assertThrows(NotFoundException.class,
                () -> findProductService.findByPriceBetween(10.0, 20.0));
    }

    @Test
    void findByQuantityGreaterThan_shouldReturnProducts_whenFound() {
        Product product = createSampleProduct();
        ProductResponse response = createSampleResponse();

        when(productRepository.findByQuantityGreaterThan(5)).thenReturn(List.of(product));
        when(converter.fromProduct(product)).thenReturn(response);

        List<ProductResponse> result = findProductService.findByQuantityGreaterThan(5);

        assertEquals(1, result.size());
    }

    @Test
    void findByQuantityGreaterThan_shouldThrow_whenEmpty() {
        when(productRepository.findByQuantityGreaterThan(100)).thenReturn(List.of());

        assertThrows(NotFoundException.class,
                () -> findProductService.findByQuantityGreaterThan(100));
    }

    @Test
    void findByStatus_shouldReturnProducts_whenFound() {
        Product product = createSampleProduct();
        ProductResponse response = createSampleResponse();

        when(productRepository.findByStatus(ProductStatus.AVAILABLE)).thenReturn(List.of(product));
        when(converter.fromProduct(product)).thenReturn(response);

        List<ProductResponse> result = findProductService.findByStatus(ProductStatus.AVAILABLE);

        assertEquals(1, result.size());
    }

    @Test
    void findByStatus_shouldThrow_whenEmpty() {
        when(productRepository.findByStatus(ProductStatus.OUT_OF_STOCK)).thenReturn(List.of());

        assertThrows(NotFoundException.class,
                () -> findProductService.findByStatus(ProductStatus.OUT_OF_STOCK));
    }

    @Test
    void findByCategoryName_shouldReturnProducts() {
        String categoryName = "GPUs";
        Category category = new Category();
        Product product = new Product();
        ProductResponse response = new ProductResponse();

        when(findCategoryService.findByNameOrThrow(categoryName)).thenReturn(category);
        when(productRepository.findByCategory(category)).thenReturn(List.of(product));
        when(converter.fromProduct(product)).thenReturn(response);

        List<ProductResponse> result = findProductService.findByCategoryName(categoryName);

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }

    @Test
    void findByCategoryName_shouldThrowNotFoundException() {
        String categoryName = "NotExistCategory";
        Category category = new Category();

        when(findCategoryService.findByNameOrThrow(categoryName)).thenReturn(category);
        when(productRepository.findByCategory(category)).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> findProductService.findByCategoryName(categoryName));
    }

    @Test
    void findBySectionName_shouldReturnProducts() {
        String sectionName = "Core Components";
        Product product = new Product();
        ProductResponse response = new ProductResponse();

        when(productRepository.findAllByCategory_Section_Name(sectionName)).thenReturn(List.of(product));
        when(converter.fromProduct(product)).thenReturn(response);

        List<ProductResponse> result = findProductService.findBySectionName(sectionName);

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }

    @Test
    void findBySectionName_shouldThrowNotFoundExceptionIfEmpty() {
        String sectionName = "EmptySection";
        when(productRepository.findAllByCategory_Section_Name(sectionName)).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> findProductService.findBySectionName(sectionName));
    }

    @Test
    void findBySectionName_shouldThrowNotFoundExceptionIfNullOrEmpty() {
        assertThrows(NotFoundException.class, () -> findProductService.findBySectionName(""));
    }

    @Test
    void searchProducts_shouldReturnFilteredProducts() {
        Product product = new Product();
        product.setManufacturer("NVIDIA");
        product.setPrice(2000.0);
        product.setStatus(ProductStatus.AVAILABLE);
        Category category = new Category();
        category.setName("GPU");
        product.setCategory(category);

        ProductResponse response = new ProductResponse();

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(converter.fromProduct(product)).thenReturn(response);

        List<ProductResponse> result = findProductService.searchProducts("NVIDIA", 1500.0, 2500.0, ProductStatus.AVAILABLE, "GPU");

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }

    @Test
    void searchProducts_shouldThrowNotFoundExceptionIfNoneMatch() {
        Product product = new Product();
        product.setManufacturer("AMD");
        product.setPrice(1000.0);
        product.setStatus(ProductStatus.DISCONTINUED);
        Category category = new Category();
        category.setName("CPU");
        product.setCategory(category);

        when(productRepository.findAll()).thenReturn(List.of(product));

        assertThrows(NotFoundException.class,
                () -> findProductService.searchProducts("NVIDIA", 1500.0, 2500.0, ProductStatus.AVAILABLE, "GPU"));
    }
}