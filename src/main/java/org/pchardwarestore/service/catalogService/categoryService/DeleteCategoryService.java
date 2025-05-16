package org.pchardwarestorefour.service.catalogService.categoryService;

import lombok.AllArgsConstructor;
import org.pchardwarestorefour.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestorefour.entity.catalogEntity.Category;
import org.pchardwarestorefour.repository.catalogRepository.CategoryRepository;
import org.pchardwarestorefour.service.exception.NotFoundException;
import org.pchardwarestorefour.service.util.CatalogConverter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteCategoryService {
    private CategoryRepository categoryRepository;
    private CatalogConverter converter;

    public CategoryResponse deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Category with ID '" + id + "' not found"));
        categoryRepository.delete(category);
        return converter.fromCategory(category);
    }
}