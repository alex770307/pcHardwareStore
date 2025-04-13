package org.pchardwarestore.service.productService;

import org.pchardwarestore.entity.productEntity.Product;
import org.pchardwarestore.repository.productRepository.ProductRepository;
import org.pchardwarestore.service.util.Converter;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UpdateProductService {
    private ProductRepository repository;
    private Converter converter;

    public UpdateProductService(ProductRepository repository, Converter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public Optional<Product> updateProduct(Product product) {
        return Optional.empty();
    }
}
