package org.pchardwarestorefour.service.catalogService.productService;

import lombok.AllArgsConstructor;
import org.pchardwarestorefour.dto.catalogDto.productDto.ProductResponse;
import org.pchardwarestorefour.entity.catalogEntity.Product;
import org.pchardwarestorefour.repository.catalogRepository.ProductRepository;
import org.pchardwarestorefour.service.exception.NotFoundException;
import org.pchardwarestorefour.service.util.CatalogConverter;
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