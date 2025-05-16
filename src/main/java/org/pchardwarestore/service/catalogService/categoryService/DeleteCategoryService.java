package org.pchardwarestore.service.catalogService.categoryService;

import lombok.AllArgsConstructor;
import org.pchardwarestore.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestore.entity.catalogEntity.Category;
import org.pchardwarestore.repository.catalogRepository.CategoryRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.CatalogConverter;
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