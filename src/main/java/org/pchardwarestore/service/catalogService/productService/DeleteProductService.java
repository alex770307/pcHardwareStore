package org.pchardwarestore.service.catalogService.productService;

import lombok.AllArgsConstructor;
import org.pchardwarestore.dto.catalogDto.productDto.ProductResponse;
import org.pchardwarestore.entity.catalogEntity.Product;
import org.pchardwarestore.repository.catalogRepository.ProductRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.CatalogConverter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteProductService {
    private ProductRepository productRepository;
    private CatalogConverter converter;

    public ProductResponse deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Product with id '" + id + "' not found"));
        productRepository.delete(product);
        return converter.fromProduct(product);
    }
}