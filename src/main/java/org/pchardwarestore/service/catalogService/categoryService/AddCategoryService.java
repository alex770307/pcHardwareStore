package org.pchardwarestore.service.catalogService.categoryService;

import lombok.AllArgsConstructor;

import org.pchardwarestore.dto.catalogDto.categoryDto.AddCategoryRequest;
import org.pchardwarestore.dto.catalogDto.categoryDto.CategoryResponse;
import org.pchardwarestore.entity.catalogEntity.Category;
import org.pchardwarestore.entity.catalogEntity.CategorySection;
import org.pchardwarestore.repository.catalogRepository.CategoryRepository;
import org.pchardwarestore.service.catalogService.cotegorySectionService.FindCategorySectionService;
import org.pchardwarestore.service.exception.AlreadyExistException;
import org.pchardwarestore.service.util.CatalogConverter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddCategoryService {
    private CategoryRepository categoryRepository;
    private FindCategorySectionService findCategorySectionService;
    private CatalogConverter converter;

    public CategoryResponse addCategory(AddCategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new AlreadyExistException("Category with name '" + request.getName() + "' already exists");
        }
        CategorySection section = findCategorySectionService.findByNameOrThrow(request.getSectionName());
        Category category = converter.toCategory(request, section);
        Category saved = categoryRepository.save(category);
        return converter.fromCategory(saved);
    }
}


