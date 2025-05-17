package org.pchardwarestore.service.catalogService.productService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.dto.catalogDto.productDto.ProductResponse;
import org.pchardwarestore.entity.catalogEntity.Product;
import org.pchardwarestore.repository.catalogRepository.ProductRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.CatalogConverter;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteProductServiceTest {

    private ProductRepository productRepository;
    private CatalogConverter converter;
    private DeleteProductService deleteProductService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        converter = mock(CatalogConverter.class);
        deleteProductService = new DeleteProductService(productRepository, converter);
    }

    @Test
    void deleteProduct_shouldDeleteAndReturnResponse_whenProductExists() {
        Long productId = 1L;
        Product mockProduct = new Product();
        mockProduct.setId(productId);

        ProductResponse mockResponse = new ProductResponse();
        mockResponse.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(converter.fromProduct(mockProduct)).thenReturn(mockResponse);
        ProductResponse response = deleteProductService.deleteProduct(productId);

        assertNotNull(response);
        assertEquals(productId, response.getId());

        verify(productRepository).findById(productId);
        verify(productRepository).delete(mockProduct);
        verify(converter).fromProduct(mockProduct);
    }

    @Test
    void deleteProduct_shouldThrowNotFoundException_whenProductDoesNotExist() {
        Long productId = 2L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> deleteProductService.deleteProduct(productId));

        assertEquals("Product with id '" + productId + "' not found", exception.getMessage());
        verify(productRepository).findById(productId);
        verify(productRepository, never()).delete(any());
        verify(converter, never()).fromProduct(any());
    }
}