package org.pchardwarestore.service.catalogService.productService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.dto.catalogDto.productDto.AddProductRequest;
import org.pchardwarestore.dto.catalogDto.productDto.ProductResponse;
import org.pchardwarestore.entity.catalogEntity.Category;
import org.pchardwarestore.entity.catalogEntity.Product;
import org.pchardwarestore.repository.catalogRepository.ProductRepository;
import org.pchardwarestore.service.catalogService.categoryService.FindCategoryService;
import org.pchardwarestore.service.exception.AlreadyExistException;
import org.pchardwarestore.service.util.CatalogConverter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddProductServiceTest {


    private ProductRepository productRepository;
    private FindCategoryService findCategoryService;
    private CatalogConverter converter;
    private AddProductService addProductService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        findCategoryService = mock(FindCategoryService.class);
        converter = mock(CatalogConverter.class);
        addProductService = new AddProductService(productRepository, findCategoryService, converter);
    }

    @Test
    void addProduct_shouldAddProductSuccessfully() {
        AddProductRequest request = new AddProductRequest();
        request.setName("TestProduct");
        request.setCategoryName("TestCategory");

        Category mockCategory = new Category();
        Product mockProduct = new Product();
        Product savedProduct = new Product();

        ProductResponse expectedResponse = new ProductResponse();
        expectedResponse.setName("TestProduct");

        when(productRepository.existsByName("TestProduct")).thenReturn(false);
        when(findCategoryService.findByNameOrThrow("TestCategory")).thenReturn(mockCategory);
        when(converter.toProduct(request, mockCategory)).thenReturn(mockProduct);
        when(productRepository.save(mockProduct)).thenReturn(savedProduct);
        when(converter.fromProduct(savedProduct)).thenReturn(expectedResponse);

        ProductResponse actualResponse = addProductService.addProduct(request);

        assertNotNull(actualResponse);
        assertEquals("TestProduct", actualResponse.getName());

        verify(productRepository).existsByName("TestProduct");
        verify(findCategoryService).findByNameOrThrow("TestCategory");
        verify(productRepository).save(mockProduct);
        verify(converter).fromProduct(savedProduct);
    }

    @Test
    void addProduct_shouldThrowAlreadyExistException_whenNameAlreadyExists() {
        AddProductRequest request = new AddProductRequest();
        request.setName("ExistingProduct");

        when(productRepository.existsByName("ExistingProduct")).thenReturn(true);

        AlreadyExistException exception = assertThrows(AlreadyExistException.class,
                () -> addProductService.addProduct(request));

        assertEquals("Product with name 'ExistingProduct' already exists", exception.getMessage());

        verify(productRepository).existsByName("ExistingProduct");
        verifyNoMoreInteractions(productRepository, findCategoryService, converter);
    }
}