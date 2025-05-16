package org.pchardwarestorefour.service.catalogService.categoryService;

import lombok.AllArgsConstructor;

import org.pchardwarestorefour.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestorefour.dto.catalogDto.categoryDto.UpdateCategoryRequest;
import org.pchardwarestorefour.entity.catalogEntity.Category;
import org.pchardwarestorefour.repository.catalogRepository.CategoryRepository;
import org.pchardwarestorefour.service.exception.NotFoundException;
import org.pchardwarestorefour.service.util.CatalogConverter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateCategoryService {
    private CategoryRepository categoryRepository;
    private CatalogConverter converter;

    public CategoryResponse updateCategory(UpdateCategoryRequest request, Long id) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Category with ID '" + id + "' not found"));
        if (request.getName() != null && !request.getName().isBlank()) {
            existing.setName(request.getName());
        }
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            existing.setDescription(request.getDescription());
        }

        Category updated = categoryRepository.save(existing);
        return converter.fromCategory(updated);
    }
}