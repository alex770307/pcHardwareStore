package org.pchardwarestore.service.catalogService.productService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.dto.catalogDto.productDto.ProductResponse;
import org.pchardwarestore.dto.catalogDto.productDto.UpdateProductRequest;
import org.pchardwarestore.entity.catalogEntity.Product;
import org.pchardwarestore.entity.catalogEntity.ProductStatus;
import org.pchardwarestore.repository.catalogRepository.ProductRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.CatalogConverter;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateProductServiceTest {

    private ProductRepository productRepository;
    private CatalogConverter converter;
    private UpdateProductService updateProductService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        converter = mock(CatalogConverter.class);
        updateProductService = new UpdateProductService(productRepository, converter);
    }

    @Test
    void updateProduct_shouldUpdateAndReturnResponse() {
        Long productId = 1L;

        UpdateProductRequest request = new UpdateProductRequest();
        request.setName("Updated Product");
        request.setDescription("Updated Description");
        request.setManufacturer("Updated Manufacturer");
        request.setPrice(999.99);
        request.setQuantity(50);
        request.setStatus("ACTIVE");

        Product existingProduct = new Product();
        existingProduct.setId(productId);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName(request.getName());
        updatedProduct.setDescription(request.getDescription());
        updatedProduct.setManufacturer(request.getManufacturer());
        updatedProduct.setPrice(request.getPrice());
        updatedProduct.setQuantity(request.getQuantity());
        updatedProduct.setStatus(ProductStatus.AVAILABLE);
        updatedProduct.setLastUpdateDate(LocalDateTime.now());

        ProductResponse expectedResponse = new ProductResponse();

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(converter.toProductStatus(request.getStatus())).thenReturn(ProductStatus.AVAILABLE);
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);
        when(converter.fromProduct(updatedProduct)).thenReturn(expectedResponse);

        ProductResponse result = updateProductService.updateProduct(request, productId);

        assertEquals(expectedResponse, result);
        verify(productRepository).save(existingProduct);
    }

    @Test
    void updateProduct_shouldThrowNotFoundException_whenProductNotFound() {
        Long productId = 42L;
        UpdateProductRequest request = new UpdateProductRequest();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> updateProductService.updateProduct(request, productId));
        verify(productRepository, never()).save(any());
    }

}