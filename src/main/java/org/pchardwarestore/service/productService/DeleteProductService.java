package org.pchardwarestore.service.productService;

import org.pchardwarestore.dto.GeneralResponse;
import org.pchardwarestore.dto.productDto.ProductResponseDto;
import org.pchardwarestore.entity.productEntity.Product;
import org.pchardwarestore.repository.productRepository.ProductRepository;
import org.pchardwarestore.service.util.Converter;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DeleteProductService {

    private ProductRepository repository;
    private Converter converter;

    public DeleteProductService(ProductRepository repository, Converter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public GeneralResponse<ProductResponseDto> deleteProductById(Long id) {
        Optional<Product> deletedProductOptional = repository.deleteProductById(id);
        if (deletedProductOptional.isPresent()) {
            Product deletedProduct = deletedProductOptional.get();
            ProductResponseDto productResponseDto = converter.toDto(deletedProduct);
            return new GeneralResponse<>(productResponseDto);
        }
        GeneralResponse<ProductResponseDto> generalResponse = new GeneralResponse<>(null);
        generalResponse.addError("Product with id = " + id + " not found.");
        return generalResponse;
    }
}
