package org.pchardwarestorefour.service.catalogService.productService;

import lombok.AllArgsConstructor;
import org.pchardwarestorefour.dto.catalogDto.productDto.AddProductRequest;
import org.pchardwarestorefour.dto.catalogDto.productDto.ProductResponse;
import org.pchardwarestorefour.entity.catalogEntity.Category;
import org.pchardwarestorefour.entity.catalogEntity.Product;
import org.pchardwarestorefour.repository.catalogRepository.ProductRepository;
import org.pchardwarestorefour.service.catalogService.categoryService.FindCategoryService;
import org.pchardwarestorefour.service.exception.AlreadyExistException;
import org.pchardwarestorefour.service.util.CatalogConverter;
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
