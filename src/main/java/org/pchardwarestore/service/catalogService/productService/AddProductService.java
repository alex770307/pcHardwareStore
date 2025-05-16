package org.pchardwarestore.service.catalogService.productService;

import lombok.AllArgsConstructor;
import org.pchardwarestore.dto.catalogDto.productDto.AddProductRequest;
import org.pchardwarestore.dto.catalogDto.productDto.ProductResponse;
import org.pchardwarestore.entity.catalogEntity.Category;
import org.pchardwarestore.entity.catalogEntity.Product;
import org.pchardwarestore.repository.catalogRepository.ProductRepository;
import org.pchardwarestore.service.catalogService.categoryService.FindCategoryService;
import org.pchardwarestore.service.exception.AlreadyExistException;
import org.pchardwarestore.service.util.CatalogConverter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddProductService {
    private ProductRepository productRepository;
    private FindCategoryService findCategoryService;
    private CatalogConverter converter;

    public ProductResponse addProduct(AddProductRequest request) {
        if (productRepository.existsByName(request.getName())) {

            throw new AlreadyExistException("Product with name '" + request.getName() + "' already exists");
        }
        Category category = findCategoryService.findByNameOrThrow(request.getCategoryName());
        Product product = converter.toProduct(request, category);
        Product saved = productRepository.save(product);
        return converter.fromProduct(saved);
    }
}
