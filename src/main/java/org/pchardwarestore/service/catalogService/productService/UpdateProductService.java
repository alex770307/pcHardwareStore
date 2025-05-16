package org.pchardwarestorefour.service.catalogService.productService;

import lombok.AllArgsConstructor;
import org.pchardwarestorefour.dto.catalogDto.productDto.ProductResponse;
import org.pchardwarestorefour.dto.catalogDto.productDto.UpdateProductRequest;
import org.pchardwarestorefour.entity.catalogEntity.Product;
import org.pchardwarestorefour.repository.catalogRepository.ProductRepository;
import org.pchardwarestorefour.service.exception.NotFoundException;
import org.pchardwarestorefour.service.util.CatalogConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UpdateProductService {
    private ProductRepository productRepository;
    private CatalogConverter converter;

    public ProductResponse updateProduct(UpdateProductRequest request, Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() ->
//                        new IllegalArgumentException("Product with ID '" + id + "' not found"));
                        new NotFoundException("Product with ID '" + id + "' not found"));

        if (request.getName() != null && !request.getName().isBlank()) {
            existingProduct.setName(request.getName());
        }
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            existingProduct.setDescription(request.getDescription());
        }
        if (request.getManufacturer() != null && !request.getManufacturer().isBlank()) {
            existingProduct.setManufacturer(request.getManufacturer());
        }
        if (request.getPrice() != null) {
            existingProduct.setPrice(request.getPrice());
        }
        if (request.getQuantity() != null) {
            existingProduct.setQuantity(request.getQuantity());
        }
        existingProduct.setLastUpdateDate(LocalDateTime.now());

        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            existingProduct.setStatus(converter.toProductStatus(request.getStatus()));
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return converter.fromProduct(updatedProduct);
    }
}

